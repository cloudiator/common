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
import de.uniulm.omi.cloudiator.domain.LoginNameSupplier;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Deprecated
@Entity
public class Image extends RemoteResourceInLocation implements LoginNameSupplier {

  /**
   * Own attributes
   */
  @Column(updatable = false, nullable = false)
  private String name;
  @Nullable
  private String loginUsernameOverride;
  @Nullable
  private String loginPasswordOverride;

  /**
   * Owned relations
   */
  @ManyToOne(optional = false)
  private OperatingSystem operatingSystem;

  /**
   * Foreign relations
   */
  @OneToMany(mappedBy = "image", cascade = CascadeType.REMOVE)
  private List<VirtualMachineTemplate> virtualMachineTemplates;

  /**
   * Empty constructor for hibernate.
   */
  protected Image() {
  }

  public Image(@Nullable String remoteId, @Nullable String providerId, @Nullable String swordId,
      Cloud cloud, @Nullable Location location, String name, OperatingSystem operatingSystem,
      @Nullable String loginUsernameOverride, @Nullable String loginPasswordOverride) {
    super(remoteId, providerId, swordId, cloud, null, location);

    checkNotNull(name);
    checkArgument(!name.isEmpty());
    this.name = name;

    this.operatingSystem = operatingSystem;

    this.loginUsernameOverride = loginUsernameOverride;
    this.loginPasswordOverride = loginPasswordOverride;
  }

  public String name() {
    return name;
  }

  public OperatingSystem operatingSystem() {
    return operatingSystem;
  }

  public List<VirtualMachineTemplate> virtualMachineTemplatesUsedFor() {
    return ImmutableList.copyOf(virtualMachineTemplates);
  }

  @Override
  public String loginName() {
    if (loginUsernameOverride != null) {
      return loginUsernameOverride;
    }
    return operatingSystem.operatingSystemFamily().loginName();
  }

  public Optional<String> getLoginPasswordOverride() {
    return Optional.ofNullable(loginPasswordOverride);
  }
}
