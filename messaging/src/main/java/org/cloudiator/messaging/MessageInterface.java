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
import org.cloudiator.messages.General;
import org.cloudiator.messages.General.Error;

/**
 * Created by daniel on 17.03.17.
 */
public interface MessageInterface {

  <T extends Message> Subscription subscribe(String topic, Parser<T> parser,
      MessageCallback<T> callback);

  <T extends Message> Subscription subscribe(Class<T> messageClass, Parser<T> parser,
      MessageCallback<T> callback);

  void publish(String topic, Message message);

  void publish(Message message);

  void publish(String topic, String id, Message message);

  <T extends Message, S extends Message> void callAsync(String requestTopic, T request,
      String responseTopic,
      Class<S> responseClass, ResponseCallback<S> responseConsumer);

  <T extends Message, S extends Message> void callAsync(T request,
      Class<S> responseClass, ResponseCallback<S> responseConsumer);

  <T extends Message, S extends Message> S call(String requestTopic, T request,
      String responseTopic,
      Class<S> responseClass) throws ResponseException;

  <T extends Message, S extends Message> S call(String requestTopic, T request,
      String responseTopic,
      Class<S> responseClass, long timeout) throws ResponseException;

  <T extends Message, S extends Message> S call(T request,
      Class<S> responseClass) throws ResponseException;

  <T extends Message, S extends Message> S call(T request,
      Class<S> responseClass, long timeout) throws ResponseException;

  void reply(String originId, Message message);

  <T extends Message> void reply(Class<T> originalMessage, String originId, General.Error error);

  void reply(String topic, String originId, Message message);

  void reply(String topic, String originId, Error error);
}
