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

import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import de.uniulm.omi.cloudiator.util.execution.LoggingScheduledThreadPoolExecutor;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.Subscription;
import org.cloudiator.messaging.SubscriptionImpl;

/**
 * Created by daniel on 17.03.17.
 */
public class KafkaMessageInterface implements MessageInterface {

  private static final ExecutorService EXECUTOR_SERVICE =
      new LoggingScheduledThreadPoolExecutor(5);

  public KafkaMessageInterface() {

  }

  @Override
  public <T extends Message> Subscription subscribe(String topic, Parser<T> parser,
      MessageCallback<T> callback) {
    KafkaConsumer<String, T> consumer =
        new KafkaConsumer<>(Kafka.properties(), new StringDeserializer(),
            new ProtobufDeserializer<>(parser));
    consumer.subscribe(Collections.singleton(topic));
    final Future<?> future = EXECUTOR_SERVICE.submit(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        final ConsumerRecords<String, T> poll = consumer.poll(1000);
        poll.forEach(
            stringTConsumerRecord -> {
              System.out.println("Receiving message with id " + stringTConsumerRecord.key() + " - "
                  + stringTConsumerRecord.value());
              callback
                  .accept(stringTConsumerRecord.key(), stringTConsumerRecord.value());
            });
      }
    });
    return SubscriptionImpl.of(() -> future.cancel(true));
  }

  @Override
  public void publish(String topic, Message message) {
    this.publish(topic, UUID.randomUUID().toString(), message);
  }

  @Override
  public void publish(String topic, String id, Message message) {
    Kafka.Producers.kafkaProducer()
        .send(new ProducerRecord<>(topic, id, message));
  }
}
