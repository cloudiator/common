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

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.cloudiator.messaging.MessageInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daniel on 13.06.17.
 */
public class KafkaMessagingModule extends AbstractModule {

  private final KafkaContext kafkaContext;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(KafkaMessagingModule.class);

  public KafkaMessagingModule(KafkaContext kafkaContext) {
    this.kafkaContext = kafkaContext;
  }

  @Override
  protected void configure() {
    bind(MessageInterface.class).to(KafkaMessageInterface.class);
    bind(KafkaSubscriptionService.class).to(KafkaSubscriptionServiceImpl.class);
    bind(KafkaConsumerFactory.class).to(BaseKafkaConsumerFactory.class);
    bind(KafkaProducerFactory.class).to(SingletonKafkaProducerFactory.class);

    Names.bindProperties(binder(), kafkaContext.getProperties());
  }

}
