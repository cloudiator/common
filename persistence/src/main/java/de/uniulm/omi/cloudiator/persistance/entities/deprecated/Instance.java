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
import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


/**
 * Created by daniel on 12.12.14.
 */
@Deprecated
@Entity
public class Instance extends RemoteResource {

  @ManyToOne(optional = false)
  private ApplicationComponent applicationComponent;
  @ManyToOne(optional = false)
  private ApplicationInstance applicationInstance;

  @ManyToOne
  private VirtualMachine virtualMachine;
  @Nullable
  @ManyToOne
  private Tenant tenant;

  /**
   * Empty constructor for hibernate.
   */
  protected Instance() {
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

  public void setApplicationComponent(ApplicationComponent applicationComponent) {
    checkNotNull(applicationComponent);
    this.applicationComponent = applicationComponent;
  }

  public VirtualMachine getVirtualMachine() {
    return virtualMachine;
  }

  public void setVirtualMachine(VirtualMachine virtualMachine) {
    checkNotNull(virtualMachine);
    this.virtualMachine = virtualMachine;
  }

  public ApplicationInstance getApplicationInstance() {
    return applicationInstance;
  }

  public void setApplicationInstance(ApplicationInstance applicationInstance) {
    this.applicationInstance = applicationInstance;
  }

  public Set<Instance> getTargetCommunicationInstances() {

    return applicationComponent.getRequiredCommunications().stream()
        .map(communication -> communication.getProvidedPort().getApplicationComponent())
        .flatMap(applicationComponent -> applicationComponent.getInstances().stream())
        .filter(instance -> instance.applicationInstance.equals(applicationInstance))
        .collect(Collectors.toSet());
  }

  public Optional<Tenant> tenant() {
    return Optional.ofNullable(this.tenant);
  }

  public void bindTenant(Tenant tenant) {
    checkState(this.tenant == null, "Changing tenant is not permitted.");
    checkNotNull(tenant, "Setting null tenant is not allowed.");
    this.tenant = tenant;
  }

  public void unbind() {
    unbindRemoteId();
  }
}
