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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * Created by daniel on 12.03.15.
 */
@Deprecated
@Entity
public class IpAddress extends Model {

  @Column(updatable = false)
  private String ip;

  @Enumerated(EnumType.STRING)
  @Column(updatable = false)
  private IpType ipType;

  @ManyToOne(optional = false)
  private VirtualMachine virtualMachine;

  /**
   * Empty constructor for hibernate.
   */
  protected IpAddress() {
  }

  public IpAddress(VirtualMachine virtualMachine, String ip, IpType ipType) {

    checkNotNull(virtualMachine);
    checkNotNull(ip);
    checkArgument(!ip.isEmpty());
    checkNotNull(ipType);

    this.ip = ip;
    this.ipType = ipType;
    this.virtualMachine = virtualMachine;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public IpType getIpType() {
    return ipType;
  }

  public void setIpType(IpType ipType) {
    this.ipType = ipType;
  }

  public VirtualMachine getVirtualMachine() {
    return virtualMachine;
  }

  public void setVirtualMachine(VirtualMachine virtualMachine) {
    this.virtualMachine = virtualMachine;
  }

  @Override
  public String toString() {
    return ip;
  }
}
