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

import java.util.Optional;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

/**
 * Created by daniel on 12.05.15.
 */
@Entity
@Inheritance(strategy = javax.persistence.InheritanceType.TABLE_PER_CLASS)
@Deprecated
public abstract class RemoteResource extends Model {

  private RemoteState remoteState = RemoteState.INPROGRESS;
  @Nullable
  @Column(unique = true, nullable = true)
  private String remoteId;


  public RemoteResource() {

  }

  public RemoteResource(@Nullable String remoteId) {
    this.remoteId = remoteId;

  }

  public RemoteResource(@Nullable String remoteId, @Nullable RemoteState remoteState) {
    this.remoteId = remoteId;
    this.remoteState = remoteState;
  }

  public RemoteState getRemoteState() {
    if (remoteState == null) {
      return RemoteState.INPROGRESS;
    }
    return remoteState;
  }

  public void setRemoteState(RemoteState remoteState) {
    this.remoteState = remoteState;
  }

  public Optional<String> remoteId() {
    return Optional.ofNullable(remoteId);
  }

  public void bindRemoteId(String remoteId) {
    checkNotNull(remoteId, "Binding null remoteId is not allowed. Use unbind.");
    if (this.remoteId != null) {
      throw new IllegalStateException("RemoteId was already bound, unbind first.");
    }
    this.remoteId = remoteId;
  }

  public void unbindRemoteId() {
    this.remoteId = null;
  }


}
