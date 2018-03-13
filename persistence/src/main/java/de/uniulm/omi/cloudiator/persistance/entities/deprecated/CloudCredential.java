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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @todo somehow validate this constraint, only have one credential per cloud and frontend group (or
 * find a better relational schema)
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cloud_id", "tenant_id"}))
@Entity
@Deprecated
public class CloudCredential extends Model {

  @Column(nullable = false)
  private String user;

  @Lob
  @Column(nullable = false)
  private String secret;

  @ManyToOne(optional = false)
  private Cloud cloud;

  @ManyToOne(optional = false)
  private Tenant tenant;

  @ManyToMany(mappedBy = "cloudCredentials")
  private List<RemoteResourceInCloud> remoteResources;

  /**
   * Empty constructor for hibernate.
   */
  protected CloudCredential() {
  }

  public CloudCredential(Cloud cloud, Tenant tenant, String user, String secret) {

    checkNotNull(cloud);
    checkNotNull(tenant);
    checkNotNull(user);
    checkArgument(!user.isEmpty());
    checkNotNull(secret);
    checkArgument(!secret.isEmpty());

    this.cloud = cloud;
    this.tenant = tenant;
    this.user = user;
    this.secret = secret;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Cloud getCloud() {
    return cloud;
  }

  public void setCloud(Cloud cloud) {
    this.cloud = cloud;
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = tenant;
  }

  public List<RemoteResourceInCloud> remoteResources() {
    return ImmutableList.copyOf(remoteResources);
  }
}
