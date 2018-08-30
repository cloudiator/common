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

import java.util.Optional;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Frank on 20.05.2015.
 */
@Deprecated
@Entity
public class MonitorInstance extends ModelWithExternalReference {

  @ManyToOne(optional = false)
  private Monitor monitor;
  private String apiEndpoint;
  @ManyToOne(optional = true)
  @Nullable
  private VirtualMachine virtualMachine;
  @ManyToOne(optional = true)
  @Nullable
  private Component component;
  @Column()
  private Integer port;

  /**
   * Empty constructor for hibernate.
   */
  protected MonitorInstance() {
  }

  public MonitorInstance(Monitor monitor, String apiEndpoint,
      @Nullable VirtualMachine virtualMachine, @Nullable Component component,
      @Nullable Integer port) {
    this.apiEndpoint = apiEndpoint;
    this.monitor = monitor;
    this.virtualMachine = virtualMachine;
    this.component = component;
    this.port = port;
  }

  @Nullable
  public Monitor getMonitor() {
    return monitor;
  }

  @Nullable
  public VirtualMachine getVirtualMachine() {
    return virtualMachine;
  }

  @Nullable
  public Component getComponent() {
    return component;
  }

  @Nullable
  public Integer getPort() {
    return port;
  }

  public void setPort(@Nullable Integer port) {
    this.port = port;
  }

  public String getApiEndpoint() {
    return apiEndpoint;
  }

  public String getIpOfVisor() {
    // TODO: implement according to Visor distribution strategy
    if (this.getVirtualMachine() != null) {
      Optional<IpAddress> ip = this.getVirtualMachine().publicIpAddress();
      if (ip.isPresent()) {
        return ip.get().getIp();
      }
    }

    // TODO: We do not use isEmpty() as an empty string is aloud for home domain
    if (getApiEndpoint() != null) {
      return getApiEndpoint();
    }

    throw new IllegalStateException("No Visor API found!");
  }

  public String getIpOfTsdb() {
    // TODO: implement according to TSBD distribution strategy
    // TODO: currently we assume Visor and TSBD are installed equivalent
    return getIpOfVisor();
  }
}
