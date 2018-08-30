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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Created by daniel on 03.08.15.
 */
@Deprecated
@Entity
public class PortProvided extends Port {

  @OneToMany(mappedBy = "providedPort")
  List<Communication> providedCommunications;
  @Column
  private Integer port;

  /**
   * Empty constructor for hibernate.
   */
  protected PortProvided() {
  }

  public PortProvided(String name, ApplicationComponent applicationComponent, int port) {
    super(name, applicationComponent);
    checkNotNull(port);
    this.port = port;
  }

  @Override
  public Set<Communication> getAttachedCommunications() {
    if (providedCommunications == null) {
      return Collections.emptySet();
    }
    return new HashSet<>(providedCommunications);
  }

  public int getPort() {
    return port;
  }
}
