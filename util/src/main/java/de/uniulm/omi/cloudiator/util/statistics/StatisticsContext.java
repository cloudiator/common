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

package de.uniulm.omi.cloudiator.util.statistics;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.uniulm.omi.cloudiator.util.configuration.Configuration;

public class StatisticsContext {

  private final Config config;

  public StatisticsContext(Config config) {
    this.config = config;
    config.checkValid(ConfigFactory.defaultReference(), "statistics");
  }

  public StatisticsContext() {
    this(Configuration.conf());
  }

  public Boolean enabled() {
    return config.getBoolean("statistics.enabled");
  }

  public String url() {
    return config.getString("statistics.influx.url");
  }

  public String user() {
    return config.getString("statistics.influx.user");
  }

  public String password() {
    return config.getString("statistics.influx.password");
  }
}
