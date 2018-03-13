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
import java.util.Optional;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Deprecated
@Entity
public class Cloud extends Model {

  @Column(unique = true, nullable = false, updatable = false)
  private String name;
  @Nullable
  @Column(nullable = true)
  private String endpoint;
  @ManyToOne(optional = false)
  private Api api;
  @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE)
  private List<RemoteResourceInCloud>
      remoteResources;
  @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE)
  private List<VirtualMachineTemplate> virtualMachineTemplates;
  @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE)
  private List<CloudCredential>
      cloudCredentials;
  @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE)
  private List<CloudProperty>
      cloudProperties;

  /**
   * Empty constructor. Needed by hibernate.
   */
  protected Cloud() {
  }

  public Cloud(String name, @Nullable String endpoint, Api api) {
    checkNotNull(name);
    checkArgument(!name.isEmpty());
    if (endpoint != null) {
      checkArgument(!endpoint.isEmpty());
    }
    checkNotNull(api);
    this.name = name;
    this.endpoint = endpoint;
    this.api = api;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Optional<String> getEndpoint() {
    return Optional.ofNullable(endpoint);
  }

  public Api api() {
    return api;
  }

  public List<RemoteResourceInCloud> remoteResources() {
    return ImmutableList.copyOf(remoteResources);
  }

  public void setRemoteResources(List<RemoteResourceInCloud> remoteResources) {
    this.remoteResources = remoteResources;
  }

  public List<CloudCredential> getCloudCredentials() {
    return ImmutableList.copyOf(cloudCredentials);
  }

  public void addProperty(CloudProperty cloudProperty) {
    this.cloudProperties.add(cloudProperty);
  }
}
