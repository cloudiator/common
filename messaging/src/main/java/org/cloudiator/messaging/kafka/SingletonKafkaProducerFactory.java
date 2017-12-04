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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.cloudiator.messaging.kafka.Constants.KAFKA_SERVERS;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.Message;
import java.util.Properties;
import javax.inject.Named;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

@Singleton
class SingletonKafkaProducerFactory implements KafkaProducerFactory {

  private final String bootstrapServers;

  private static class ProducerSingleton {

    private static Producer<String, Message> instance = null;

    private ProducerSingleton() {
      throw new AssertionError("Do not instantiate " + this);
    }

    private static Producer<String, Message> getInstance(Properties properties) {
      if (instance == null) {
        instance = new KafkaProducer<>(properties, new StringSerializer(),
            new ProtobufSerializer());
      }
      return instance;
    }
  }


  @Inject
  SingletonKafkaProducerFactory(@Named(KAFKA_SERVERS) String bootstrapServers) {
    checkNotNull(bootstrapServers, "bootstrapServers is null");
    checkArgument(!bootstrapServers.isEmpty(), "bootstrapServers is empty.");
    this.bootstrapServers = bootstrapServers;
  }

  @Override
  public Producer<String, Message> createKafkaProducer() {
    final Properties properties = new Properties();
    properties.put("bootstrap.servers", bootstrapServers);
    properties.put("queue.buffering.max.ms", 500);
    return ProducerSingleton.getInstance(properties);
  }

}
