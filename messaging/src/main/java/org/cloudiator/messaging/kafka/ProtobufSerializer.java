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

import com.google.common.base.Charsets;
import com.google.protobuf.Message;

/**
 * Created by daniel on 02.03.17.
 */
public class ProtobufSerializer extends Adapter implements Serializer<Message> {

  private static final boolean DEBUG = false;

  @Override
  public byte[] serialize(String topic, Message message) {
    if (DEBUG) {
      return message.toString().getBytes(Charsets.UTF_8);
    } else {
      return message.toByteArray();
    }
  }
}
