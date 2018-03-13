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

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Deprecated
@Entity
public class Hardware extends RemoteResourceInLocation {

  @Column(updatable = false, nullable = false)
  private String name;

  @ManyToOne(optional = false)
  private HardwareOffer hardwareOffer;

  @OneToMany(mappedBy = "hardware", cascade = CascadeType.REMOVE)
  private List<VirtualMachineTemplate> virtualMachineTemplates;

  /**
   * Empty constructor for hibernate.
   */
  protected Hardware() {
  }

  public Hardware(@Nullable String remoteId, @Nullable String providerId,
      @Nullable String swordId, Cloud cloud, Location location, String name,
      HardwareOffer hardwareOffer) {
    super(remoteId, providerId, swordId, cloud, null, location);

    checkNotNull(name);
    checkArgument(!name.isEmpty());

    this.name = name;
    this.hardwareOffer = hardwareOffer;
  }

  public HardwareOffer hardwareOffer() {
    return hardwareOffer;
  }

  public List<VirtualMachineTemplate> virtualMachineTemplatesUsedFor() {
    return ImmutableList.copyOf(virtualMachineTemplates);
  }

  public String name() {
    return name;
  }

}
