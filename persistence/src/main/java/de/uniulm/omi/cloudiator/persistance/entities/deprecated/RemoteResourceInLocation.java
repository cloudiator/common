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
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by daniel on 22.09.15.
 */
@Deprecated
@Entity
public abstract class RemoteResourceInLocation extends RemoteResourceInCloud {

  @ManyToOne
  private Location location;

  /**
   * No-arg constructor for hibernate.
   */
  protected RemoteResourceInLocation() {
  }

  public RemoteResourceInLocation(Cloud cloud, Location location) {
    super(cloud);
    this.location = location;
  }

  public RemoteResourceInLocation(@Nullable String remoteId, @Nullable String providerId,
      @Nullable String swordId, Cloud cloud, @Nullable CloudCredential owner,
      @Nullable Location location) {
    super(remoteId, providerId, swordId, cloud, owner);
    this.location = location;
  }

  public Optional<Location> location() {
    return Optional.ofNullable(location);
  }

}
