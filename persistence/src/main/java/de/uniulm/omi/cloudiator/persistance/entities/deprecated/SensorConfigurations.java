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

package de.uniulm.omi.cloudiator.persistance.entities.deprecated;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;

/**
 * Created by Frank on 17.03.2016.
 */
@Deprecated
@Entity
public class SensorConfigurations extends Model {

  @ElementCollection
  @MapKeyColumn(name = "configName")
  @Column(name = "configValue")
  private Map<String, String> configs;

  /**
   * Empty constructor for hibernate.
   */
  protected SensorConfigurations() {

  }

  public Map<String, String> configs() {
    return ImmutableMap.copyOf(configs);
  }

  public void addTag(String configName, String configValue) {
    this.configs.put(configName, configValue);
  }
}
