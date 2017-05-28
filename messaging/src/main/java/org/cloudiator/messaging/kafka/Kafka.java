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

import com.google.protobuf.Message;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.cloudiator.messaging.MessageInterface;

/**
 * Created by daniel on 02.03.17.
 */
public class Kafka {

  private final static MessageInterface MESSAGE_INTERFACE = new KafkaMessageInterface();

  public static MessageInterface messageInterface() {
    return MESSAGE_INTERFACE;
  }

  protected static Properties properties() {
    java.util.Properties props = new Properties();
    props.put("bootstrap.servers", "134.60.47.167:9092");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("group.id", "myUniqueGroup");
    return props;
  }

  public enum Queue {
    DISCOVERY("discovery"), CLOUD("cloud");

    private final String queueName;


    Queue(final String queueName) {
      checkNotNull(queueName, "queueName is null");
      checkArgument(!queueName.isEmpty(), "queueName is empty");
      this.queueName = queueName;
    }

    public String queueName() {
      return queueName;
    }
  }

  protected static class Producers {

    private Producers() {

    }

    protected static Producer<String, Message> kafkaProducer() {
      return new KafkaProducer<>(properties(), new StringSerializer(),
          new ProtobufSerializer());
    }

  }

}