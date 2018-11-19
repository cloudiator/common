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

import com.google.common.base.MoreObjects;
import org.cloudiator.messaging.MessageCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RunnableMessageCallback<T> implements MessageCallback<T>, Runnable {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RunnableMessageCallback.class);
  private final MessageCallback<T> delegate;
  private final String id;
  private final T content;

  private RunnableMessageCallback(MessageCallback<T> delegate, String id, T content) {
    this.delegate = delegate;
    this.id = id;
    this.content = content;
  }

  static <T> RunnableMessageCallback<T> of(MessageCallback<T> delegate, String id, T content) {
    return new RunnableMessageCallback<>(delegate, id, content);
  }

  @Override
  public void accept(String id, T content) {
    delegate.accept(id, content);
  }

  @Override
  public void run() {
    LOGGER.trace(String
        .format("%s is starting execution.", this));
    accept(this.id, this.content);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("delegate", delegate).add("id", id)
        .add("content", content).toString();
  }
}
