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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by daniel on 11.05.15.
 */
@Deprecated
@Entity
public class ApplicationInstance extends Model {

  @ManyToOne(optional = false)
  private Application application;
  @OneToMany(mappedBy = "applicationInstance")
  private List<Instance> instances;

  /**
   * No-args constructor for hibernate.
   */
  protected ApplicationInstance() {

  }

  public ApplicationInstance(Application application) {
    this.application = application;
  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public List<Instance> getInstances() {
    return instances;
  }

  public void setInstances(List<Instance> instances) {
    this.instances = instances;
  }

  public Set<VirtualMachine> virtualMachines() {
    return instances.stream().map(Instance::getVirtualMachine).collect(Collectors.toSet());
  }
}
