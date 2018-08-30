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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * Created by daniel on 19.12.14.
 */
@Table(
    indexes = {@Index(columnList = "token", unique = true)})
@Deprecated
@Entity
public class ApiAccessToken
    extends Model {

  private static final long VALIDITY = (long) 5 * 60 * 1000;

  @Column(nullable = false)
  private long createdOn;

  @Column(nullable = false)
  private long expiresAt;

  @Lob
  @Column(nullable = false, unique = true)
  private String token;

  @ManyToOne(optional = false)
  private FrontendUser frontendUser;

  /**
   * Empty constructor for hibernate.
   */
  protected ApiAccessToken() {
  }

  public ApiAccessToken(final FrontendUser frontendUser, final String token) {
    this.frontendUser = frontendUser;
    this.token = token;
  }

  @PrePersist
  protected void onCreate() {
    long currentTime = System.currentTimeMillis();
    this.createdOn = currentTime;
    this.expiresAt = currentTime + VALIDITY;
  }

  public long getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(long createdOn) {
    this.createdOn = createdOn;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public FrontendUser getFrontendUser() {
    return frontendUser;
  }

  public void setFrontendUser(FrontendUser frontendUser) {
    this.frontendUser = frontendUser;
  }

  public long getExpiresAt() {
    return expiresAt;
  }

  protected void setExpiresAt(final long expiresAt) {
    this.expiresAt = expiresAt;
  }
}
