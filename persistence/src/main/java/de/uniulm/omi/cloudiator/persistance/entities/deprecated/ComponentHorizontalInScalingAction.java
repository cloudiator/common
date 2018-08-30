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

import javax.persistence.Entity;

/**
 * Created by Frank on 20.05.2015.
 */
@Deprecated
@Entity
public class ComponentHorizontalInScalingAction extends ComponentHorizontalScalingAction {

  /**
   * Empty constructor for hibernate.
   */
  protected ComponentHorizontalInScalingAction() {
  }

  public ComponentHorizontalInScalingAction(Long amount, Long min, Long max, Long count,
      ApplicationComponent applicationComponent) {
    super(amount, min, max, count, applicationComponent);
  }
}
