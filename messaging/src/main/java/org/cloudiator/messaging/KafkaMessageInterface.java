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

package org.cloudiator.messaging;

import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import de.uniulm.omi.cloudiator.util.execution.LoggingScheduledThreadPoolExecutor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.cloudiator.messaging.kafka.Kafka;
import org.cloudiator.messaging.kafka.ProtobufDeserializer;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * Created by daniel on 17.03.17.
 */
public class KafkaMessageInterface implements MessageInterface {

    private static final ExecutorService EXECUTOR_SERVICE =
        new LoggingScheduledThreadPoolExecutor(5);

    public KafkaMessageInterface() {

    }

    @Override public <T extends Message> Subscription subscribe(String topic, Parser<T> parser,
        Consumer<T> callback) {
        KafkaConsumer<String, T> consumer =
            new KafkaConsumer<>(Kafka.properties(), new StringDeserializer(),
                new ProtobufDeserializer<>(parser));
        consumer.subscribe(Collections.singleton(topic));
        final Future<?> future = EXECUTOR_SERVICE.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                final ConsumerRecords<String, T> poll = consumer.poll(1000);
                poll.forEach(
                    stringTConsumerRecord -> callback.accept(stringTConsumerRecord.value()));
            }
        });
        return SubscriptionImpl.of(() -> future.cancel(true));
    }

    @Override public void publish(String topic, Message message) {
        Kafka.Producers.kafkaProducer()
            .send(new ProducerRecord<>(topic, UUID.randomUUID().toString(), message));
    }
}
