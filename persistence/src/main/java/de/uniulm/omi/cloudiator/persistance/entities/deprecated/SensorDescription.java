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

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Frank on 20.05.2015.
 */
@Deprecated
@Entity
public class SensorDescription extends Model {

  @Column(updatable = false)
  private String className;
  @Column(nullable = false, updatable = false)
  private String metricName;
  @Column(nullable = false, updatable = false)
  private Boolean isVmSensor;
  @Column(nullable = false, updatable = false)
  private Boolean isPush = false;

  /**
   * Empty constructor for hibernate.
   */
  protected SensorDescription() {
  }

  public SensorDescription(String className, String metricName, Boolean isVmSensor,
      Boolean isPush) {
    checkNotNull(className);
    checkNotNull(metricName);
    this.className = className;
    this.metricName = metricName;
    this.isVmSensor = isVmSensor;
    this.isPush = isPush;
  }

  public Boolean isVmSensor() {
    return isVmSensor;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getMetricName() {
    return metricName;
  }

  public void setMetricName(String metricName) {
    this.metricName = metricName;
  }

  public Boolean isPush() {
    return isPush;
  }

  public void setPush(Boolean push) {
    isPush = push;
  }

  public Boolean getVmSensor() {
    return isVmSensor;
  }

  public void setVmSensor(Boolean isVmSensor) {
    this.isVmSensor = isVmSensor;
  }
}
