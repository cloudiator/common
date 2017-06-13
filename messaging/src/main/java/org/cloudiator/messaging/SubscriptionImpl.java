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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daniel on 17.03.17.
 */
public class SubscriptionImpl implements Subscription {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SubscriptionImpl.class);

  private final Runnable cancelHook;

  private SubscriptionImpl(Runnable cancelHook) {
    this.cancelHook = cancelHook;
  }

  public static Subscription of(Runnable cancelHook) {
    return new SubscriptionImpl(cancelHook);
  }

  @Override
  public void cancel() {
    LOGGER.debug(String.format("Canceling subscription %s.", this));
    cancelHook.run();
  }
}
