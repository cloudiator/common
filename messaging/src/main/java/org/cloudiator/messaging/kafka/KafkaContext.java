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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.uniulm.omi.cloudiator.util.configuration.Configuration;
import java.util.Properties;

public class KafkaContext {

  private final Config config;

  public KafkaContext() {
    this(Configuration.conf());
  }

  public KafkaContext(Config config) {
    this.config = config;
    config.checkValid(ConfigFactory.defaultReference(), "kafka");
  }

  public Properties getProperties() {
    final Properties properties = new Properties();
    config.entrySet()
        .forEach(stringConfigValueEntry -> properties.setProperty(stringConfigValueEntry.getKey(),
            String.valueOf(stringConfigValueEntry.getValue().unwrapped())));
    return properties;
  }


}
