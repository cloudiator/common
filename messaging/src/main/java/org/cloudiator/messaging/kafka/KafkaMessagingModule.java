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
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import de.uniulm.omi.cloudiator.util.PropertiesLoader;
import java.io.IOException;
import java.util.Properties;
import org.cloudiator.messaging.MessageInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daniel on 13.06.17.
 */
public class KafkaMessagingModule extends AbstractModule {

  private static String CONFIG_FILE_LOCATION = System.getProperty("kafkaConfigFile");
  private static final String DEFAULT_CONFIG_FILE_LOCATION = "kafka.properties";

  private static final Logger LOGGER =
      LoggerFactory.getLogger(KafkaMessagingModule.class);

  @Override
  protected void configure() {
    bind(MessageInterface.class).to(KafkaMessageInterface.class).in(Singleton.class);

    if (CONFIG_FILE_LOCATION == null) {
      CONFIG_FILE_LOCATION = DEFAULT_CONFIG_FILE_LOCATION;
      LOGGER.warn("Could not read system property. Falling back to default.");
    }
    LOGGER.debug("Trying to open kafkaConfigFile from " + CONFIG_FILE_LOCATION);

    try {
      Properties properties = PropertiesLoader.loadPropertiesFrom(CONFIG_FILE_LOCATION);
      Names.bindProperties(binder(), properties);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
