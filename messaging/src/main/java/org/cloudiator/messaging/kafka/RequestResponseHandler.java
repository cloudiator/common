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

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import org.cloudiator.messages.General.Error;
import org.cloudiator.messages.General.Response;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;
import org.cloudiator.messaging.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daniel on 24.05.17.
 */
public class RequestResponseHandler {


  private final MessageInterface messageInterface;
  private Map<String, ResponseCallback> waitingCallbacks = new ConcurrentHashMap<>();
  private Map<String, Subscription> activeSubscriptions = new ConcurrentHashMap<>();
  private static final Logger LOGGER =
      LoggerFactory.getLogger(RequestResponseHandler.class);

  public RequestResponseHandler() {
    messageInterface = Kafka.messageInterface();
  }

  public <T extends Message, S extends Message> void callAsync(String requestTopic, T request,
      String responseTopic,
      Class<S> responseClass, ResponseCallback<S> responseConsumer) {

    //generate message ID
    String messageId = UUID.randomUUID().toString();

    //add waiting callback
    waitingCallbacks.put(messageId, responseConsumer);

    //subscribe to responseTopic
    final Subscription subscription = messageInterface
        .subscribe(responseTopic, Response.parser(), new MessageCallback<Response>() {
          @Override
          public void accept(String id, Response response) {
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
              if (activeSubscriptions.containsKey(messageId)) {
                activeSubscriptions.get(messageId).cancel();
              } else {
                LOGGER.warn(
                    String.format("Tried to cancel subscription for message %s, but found none.",
                        messageId));
              }
              waitingCallbacks.remove(response.getCorrelation());
            }
          }
        });
    activeSubscriptions.put(messageId, subscription);

    //send the message
    System.out.println("Sending message with id " + messageId + " - " + request);
    messageInterface.publish(requestTopic, messageId, request);
  }

  public <T extends Message, S extends Message> S call(String requestTopic, T request,
      String responseTopic,
      Class<S> responseClass) throws ResponseException {

    final SyncResponse<S> response = new SyncResponse<>();

    this.callAsync(requestTopic, request, responseTopic, responseClass, new ResponseCallback<S>() {
      @Override
      public void accept(@Nullable S content, @Nullable Error error) {
        synchronized (response) {
          response.set(content, error);
          response.notify();
        }
      }
    });

    synchronized (response) {
      try {
        response.wait();
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
    }
    return response.getContentOrThrowException();

  }

  private static class SyncResponse<S> {

    @Nullable
    private volatile S content = null;
    @Nullable
    private volatile Error error = null;

    private S getContentOrThrowException() throws ResponseException {
      if (content != null) {
        return content;
      }
      if (error != null) {
        throw new ResponseException(error.getMessage());
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
