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

import com.google.inject.Inject;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import java.util.Properties;
import javax.inject.Named;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

class BaseKafkaConsumerFactory implements KafkaConsumerFactory {

  private final String bootstrapServers;
  private final String groupId;

  @Inject
  BaseKafkaConsumerFactory(@Named(Constants.KAFKA_SERVERS) String bootstrapServers,
      @Named(Constants.KAFKA_GROUP_ID) String groupId) {
    checkNotNull(bootstrapServers, "bootstrapServers is null");
    checkArgument(!bootstrapServers.isEmpty(), "bootstrapServers is empty");
    checkNotNull(groupId, "groupId is null");
    checkArgument(!groupId.isEmpty(), "groupId is empty");
    this.bootstrapServers = bootstrapServers;
    this.groupId = groupId;
  }

  @Override
  public <T extends Message> Consumer<String, T> createKafkaConsumer(Parser<T> parser) {
    Properties properties = new Properties();
    properties.put("bootstrap.servers", bootstrapServers);
    properties.put("group.id", groupId);
    properties.put("enable.auto.commit", true);
    properties.put("auto.commit.interval.ms", 1000);
    properties.put("fetch.wait.max.ms", 1000);
    properties.put("fetch.error.backoff.ms", 1000);
    return new KafkaConsumer<>(properties, new StringDeserializer(),
        new ProtobufDeserializer<>(parser));
  }

}
