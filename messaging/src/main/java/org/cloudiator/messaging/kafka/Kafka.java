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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Inject;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import java.util.Properties;
import javax.inject.Named;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Created by daniel on 02.03.17.
 */
public class Kafka {

  private final KafkaProducerFactory kafkaProducerFactory;
  private final KafkaConsumerFactory kafkaConsumerFactory;

  @Inject
  private Kafka(@Named("bootstrap.servers") String bootstrapServers,
      @Named("group.id") String groupId) {

    checkNotNull(bootstrapServers, "bootstrapServers is null");
    checkNotNull(groupId, "groupId is null");

    kafkaProducerFactory = new KafkaProducerFactory(bootstrapServers);
    kafkaConsumerFactory = new KafkaConsumerFactory(bootstrapServers, groupId);

  }

  public KafkaProducerFactory producerFactory() {
    return kafkaProducerFactory;
  }

  public KafkaConsumerFactory consumerFactory() {
    return kafkaConsumerFactory;
  }

  static class KafkaProducerFactory {

    private final String bootstrapServers;

    private static class ProducerSingleton {

      private static Producer<String, Message> instance = null;

      private ProducerSingleton() {
        throw new AssertionError("Do not instantiate " + this);
      }

      private static Producer<String, Message> getInstance(Properties properties) {
        if (instance == null) {
          instance = new KafkaProducer<String, Message>(properties, new StringSerializer(),
              new ProtobufSerializer());
        }
        return instance;
      }
    }


    private KafkaProducerFactory(String bootstrapServers) {
      this.bootstrapServers = bootstrapServers;
    }

    Producer<String, Message> kafkaProducer() {
      final Properties properties = new Properties();
      properties.put("bootstrap.servers", bootstrapServers);
      return ProducerSingleton.getInstance(properties);
    }

  }

  static class KafkaConsumerFactory {

    private final String bootstrapServers;
    private final String groupId;

    private KafkaConsumerFactory(String bootstrapServers, String groupId) {
      checkNotNull(bootstrapServers, "bootstrapServers is null");
      checkNotNull(groupId, "groupId is null");
      this.bootstrapServers = bootstrapServers;
      this.groupId = groupId;
    }

    <T extends Message> Consumer<String, T> kafkaConsumer(Parser<T> parser) {
      Properties properties = new Properties();
      properties.put("bootstrap.servers", bootstrapServers);
      properties.put("group.id", groupId);
      return new KafkaConsumer<>(properties, new StringDeserializer(),
          new ProtobufDeserializer<>(parser));
    }

  }

}
