/*
 * Copyright 2017 University of Ulm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudiator.messaging.kafka;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import de.uniulm.omi.cloudiator.util.execution.LoggingScheduledThreadPoolExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.cloudiator.messages.General.Error;
import org.cloudiator.messages.General.Response;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;
import org.cloudiator.messaging.SubscribtionImpl;
import org.cloudiator.messaging.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daniel on 17.03.17.
 */
public class KafkaMessageInterface implements MessageInterface, AutoCloseable {

  private static final ExecutorService EXECUTOR_SERVICE =
      new LoggingScheduledThreadPoolExecutor(5);
  private final KafkaRequestResponseHandler kafkaRequestResponseHandler = new KafkaRequestResponseHandler();
  private static final Logger LOGGER =
      LoggerFactory.getLogger(KafkaMessageInterface.class);
  private final Kafka kafka;
  private final Map<String, Consumer> consumerPerTopic = Maps.newConcurrentMap();
  private final Map<String, List<MessageCallback>> callbacksPerTopic = Maps.newConcurrentMap();

  @Inject
  KafkaMessageInterface(Kafka kafka) {
    this.kafka = kafka;
  }

  private synchronized <T extends Message> void createConsumerForTopicIfNecessary(String topic,
      Parser<T> parser) {

    if (consumerPerTopic.containsKey(topic)) {
      return;
    }

    final Consumer<String, T> kafkaConsumer = kafka.consumerFactory().kafkaConsumer(parser);
    kafkaConsumer.subscribe(Collections.singleton(topic));
    consumerPerTopic.put(topic, kafkaConsumer);

    final Future<?> submit = EXECUTOR_SERVICE.submit(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        final ConsumerRecords<String, T> poll = kafkaConsumer.poll(1000);
        poll.forEach(
            consumerRecord -> {

              callbacksPerTopic.get(topic).forEach(
                  messageCallback -> {
                    LOGGER.debug(String.format(
                        "Receiving message with id %s and content %s. Calling registered callback %s",
                        consumerRecord.key(), consumerRecord.value(), messageCallback));
                    //todo we probably need to check, that no different types of callbacks
                    //todo are registered for a topic
                    //noinspection unchecked
                    messageCallback.accept(consumerRecord.key(), consumerRecord.value());
                  });
            });
      }
    });

    @SuppressWarnings("unchecked") final Consumer<String, T> consumer = consumerPerTopic
        .putIfAbsent(topic, kafka.consumerFactory().kafkaConsumer(parser));
  }

  @Override
  public synchronized <T extends Message> Subscription subscribe(String topic, Parser<T> parser,
      MessageCallback<T> callback) {

    LOGGER.debug(String
        .format("Registering new subscription for topic %s, with parser %s and callback %s.",
            topic, parser, callback));

    callbacksPerTopic.putIfAbsent(topic, new ArrayList<>());
    callbacksPerTopic.get(topic).add(callback);

    createConsumerForTopicIfNecessary(topic, parser);

    //todo need to remove last consumer from execution service?
    return new SubscribtionImpl(() -> callbacksPerTopic.get(topic).remove(callback));
  }

  @Override
  public <T extends Message> Subscription subscribe(Class<T> messageClass, Parser<T> parser,
      MessageCallback<T> callback) {
    return subscribe(messageClass.getSimpleName(), parser, callback);
  }

  @Override
  public void publish(String topic, Message message) {
    this.publish(topic, UUID.randomUUID().toString(), message);
  }

  @Override
  public void publish(Message message) {
    publish(message.getClass().getSimpleName(), message);
  }

  @Override
  public void publish(String topic, String id, Message message) {
    LOGGER.debug(
        String.format("Publishing new message %s on topic %s with id %s.", message, topic, id));
    kafka.producerFactory().kafkaProducer().send(new ProducerRecord<>(topic, id, message));

  }

  @Override
  public <T extends Message, S extends Message> void callAsync(String requestTopic, T request,
      String responseTopic, Class<S> responseClass, ResponseCallback<S> responseConsumer) {

    LOGGER.debug(
        String.format(
            "Async call to requestTopic %s with request %s. Response Topic is %s, using class %s and consumer %s",
            requestTopic, request, responseTopic, responseClass, responseConsumer));

    kafkaRequestResponseHandler
        .callAsync(requestTopic, request, responseTopic, responseClass, responseConsumer);
  }

  @Override
  public <T extends Message, S extends Message> void callAsync(T request, Class<S> responseClass,
      ResponseCallback<S> responseConsumer) {
    callAsync(request.getClass().getSimpleName(), request, responseClass.getSimpleName(),
        responseClass,
        responseConsumer);
  }

  @Override
  public <T extends Message, S extends Message> S call(String requestTopic, T request,
      String responseTopic, Class<S> responseClass) throws ResponseException {

    LOGGER.debug(String.format(
        "Call (sync) to requestTopic %s with request %s. Response Topic is %s, using class %s",
        requestTopic, request, responseTopic, responseClass));

    return kafkaRequestResponseHandler
        .call(requestTopic, request, responseTopic, responseClass, 0L);
  }

  @Override
  public <T extends Message, S extends Message> S call(String requestTopic, T request,
      String responseTopic, Class<S> responseClass, long timeout) throws ResponseException {

    LOGGER.debug(String.format(
        "Call (sync) to requestTopic %s with request %s. Response Topic is %s, using class %s and timeout %s",
        requestTopic, request, responseTopic, responseClass, timeout));

    return kafkaRequestResponseHandler
        .call(requestTopic, request, responseTopic, responseClass, timeout);
  }

  @Override
  public <T extends Message, S extends Message> S call(T request, Class<S> responseClass)
      throws ResponseException {

    LOGGER.debug(String.format(
        "Call (sync) with request %s. Response Class is %s.",
        request, responseClass));

    return kafkaRequestResponseHandler
        .call(request.getClass().getSimpleName(), request, responseClass.getSimpleName(),
            responseClass, 0L);
  }

  @Override
  public <T extends Message, S extends Message> S call(T request, Class<S> responseClass,
      long timeout) throws ResponseException {

    LOGGER.debug(String.format(
        "Call (sync) with request %s. Response Class is %s. Timeout is %s.",
        request, responseClass, timeout));

    return kafkaRequestResponseHandler
        .call(request.getClass().getSimpleName(), request, responseClass.getSimpleName(),
            responseClass,
            timeout);
  }

  @Override
  public void reply(String originId, Message message) {
    reply(message.getClass().getSimpleName(), originId, message);
  }

  @Override
  public <T extends Message> void reply(Class<T> originalMessage, String originId, Error error) {
    reply(originalMessage.getSimpleName(), originId, error);
  }

  @Override
  public void reply(String topic, String originId, Message message) {

    LOGGER.debug(String
        .format("Replying on topic %s for origin ID %s with message %s", topic, originId, message));

    Response response = Response.newBuilder().setCorrelation(originId).setContent(
        Any.pack(message)).build();
    this.publish(topic, response);
  }

  @Override
  public void reply(String topic, String originId, Error error) {
    Response response = Response.newBuilder().setCorrelation(originId).setError(error).build();
    this.publish(topic, response);

  }

  public void shutdown() {
    consumerPerTopic.forEach((topic, consumer) -> {
      consumer.unsubscribe();
      consumer.close();
    });
    EXECUTOR_SERVICE.shutdown();
    try {
      if (!EXECUTOR_SERVICE.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
        System.out
            .println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted during shutdown, exiting uncleanly");
    }
  }

  @Override
  public void close() throws Exception {
    shutdown();
  }

  private class KafkaRequestResponseHandler {

    private Map<String, ResponseCallback> waitingCallbacks = new ConcurrentHashMap<>();

    private <T extends Message, S extends Message> void callAsync(String requestTopic, T request,
        String responseTopic,
        Class<S> responseClass, ResponseCallback<S> responseConsumer) {

      //generate message ID
      String messageId = UUID.randomUUID().toString();

      //add waiting callback
      waitingCallbacks.put(messageId, responseConsumer);

      //subscribe to responseTopic
      KafkaMessageInterface.this
          .subscribe(responseTopic, Response.parser(), (id, response) -> {
            //suppressing warning, as we can be sure that the callback is always of the corresponding
            //type. Not using generics on the class, allows us to provide a more versatile implementation
            @SuppressWarnings("unchecked") final ResponseCallback<S> waitingCallback = waitingCallbacks
                .get(response.getCorrelation());
            if (waitingCallback == null) {
              LOGGER.warn(String
                  .format("Could not find callback for correlation id %s. Ignoring response %s",
                      response.getCorrelation(), response));
              return;
            }
            try {
              switch (response.getResponseCase()) {
                case CONTENT:
                  waitingCallback.accept(response.getContent().unpack(responseClass), null);
                  break;
                case ERROR:
                  waitingCallback.accept(null, response.getError());
                  break;
                case RESPONSE_NOT_SET:
                  throw new IllegalStateException(String
                      .format("Neither content or error were set in response message %s.",
                          response));
                default:
                  throw new AssertionError(String
                      .format("Illegal responseCase in message %s: %s", response,
                          response.getResponseCase()));
              }
            } catch (InvalidProtocolBufferException e) {
              throw new IllegalStateException(
                  String.format("Error parsing message %s.", e.getMessage()), e);
            } finally {
              waitingCallbacks.remove(response.getCorrelation());
            }
          });

      //send the message
      KafkaMessageInterface.this.publish(requestTopic, messageId, request);
    }

    private <T extends Message, S extends Message> S call(String requestTopic, T request,
        String responseTopic,
        Class<S> responseClass, long timeout) throws ResponseException {

      final SyncResponse<S> response = new SyncResponse<>();

      this.callAsync(requestTopic, request, responseTopic, responseClass,
          (content, error) -> {
            synchronized (response) {
              response.set(content, error);
              response.notify();
            }
          });

      synchronized (response) {
        //check if the response is already available
        if (!response.isAvailable()) {
          try {
            response.wait(timeout);
          } catch (InterruptedException e) {
            throw new IllegalStateException(e);
          }
        }
      }
      return response.getContentOrThrowException();

    }

    private class SyncResponse<S> {

      @Nullable
      private volatile S content = null;
      @Nullable
      private volatile Error error = null;

      private boolean isAvailable() {
        if (content != null || error != null) {
          return true;
        }
        return false;
      }

      private S getContentOrThrowException() throws ResponseException {
        if (content != null) {
          return content;
        }
        if (error != null) {
          throw new ResponseException(error.getCode(), error.getMessage());
        }
        throw new IllegalStateException();
      }

      private void set(@Nullable S content, @Nullable Error error) {
        if (this.content != null || this.error != null) {
          throw new IllegalStateException("Values can only be set once");
        }
        if (content != null && error != null) {
          throw new IllegalStateException("Only one value can be set at a time");
        }
        this.content = content;
        this.error = error;
      }

    }


  }
}
