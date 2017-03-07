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
import org.cloudiator.messages.Discovery;

/**
 * Created by daniel on 02.03.17.
 */
public class DiscoveryEventDeserializer extends Adapter
    implements Deserializer<Discovery.DiscoveryEvent> {

    @Override public Discovery.DiscoveryEvent deserialize(String topic, byte[] data) {
        try {
            return Discovery.DiscoveryEvent.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalStateException("Received unparseable message " + e.getMessage(), e);
        }
    }
}
