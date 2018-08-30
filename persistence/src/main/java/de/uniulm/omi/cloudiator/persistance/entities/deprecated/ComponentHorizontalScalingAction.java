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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Frank on 20.05.2015.
 */
@Deprecated
@Entity
public abstract class ComponentHorizontalScalingAction extends HorizontalScalingAction {

  @ManyToOne(optional = false)
  private ApplicationComponent applicationComponent;

  /**
   * Empty constructor for hibernate.
   */
  protected ComponentHorizontalScalingAction() {
  }

  public ComponentHorizontalScalingAction(Long amount, Long min, Long max, Long count,
      ApplicationComponent applicationComponent) {
    super(amount, min, max, count);
    checkNotNull(applicationComponent);
    this.applicationComponent = applicationComponent;
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
