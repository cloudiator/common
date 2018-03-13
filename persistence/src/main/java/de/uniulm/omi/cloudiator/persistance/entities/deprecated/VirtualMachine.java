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


import de.uniulm.omi.cloudiator.domain.LoginNameSupplier;
import de.uniulm.omi.cloudiator.domain.RemotePortProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by daniel on 31.10.14.
 */
@Deprecated
@Entity
public class VirtualMachine extends RemoteResourceInLocation implements LoginNameSupplier {

  @Column(unique = true, nullable = false)
  private String name;

  @Nullable
  @Column(nullable = true)
  private String generatedLoginUsername;
  @Nullable
  @Column(nullable = true)
  private String generatedLoginPassword;
  @Nullable
  @Lob
  @Column(nullable = true)
  private String generatedPrivateKey;

  @Nullable
  @ManyToOne(optional = true)
  private Image image;
  @Nullable
  @ManyToOne(optional = true)
  private Hardware hardware;

  @Nullable
  @ManyToOne(optional = true)
  private TemplateOptions templateOptions;

  @OneToMany(mappedBy = "virtualMachine")
  private List<Instance> instances;

  /**
   * Use set to avoid duplicate entries due to hibernate bug https://hibernate.atlassian.net/browse/HHH-7404
   */
  @OneToMany(mappedBy = "virtualMachine", cascade = {CascadeType.ALL}, orphanRemoval = true)
  private Set<IpAddress> ipAddresses;

  /**
   * Empty constructor for hibernate.
   */
  protected VirtualMachine() {
  }

  public VirtualMachine(@Nullable String remoteId, @Nullable String providerId,
      @Nullable String swordId, Cloud cloud, @Nullable CloudCredential owner, Location location,
      String name, @Nullable String generatedLoginUsername,
      @Nullable String generatedLoginPassword, @Nullable String generatedPrivateKey,
      @Nullable Image image, @Nullable Hardware hardware,
      @Nullable TemplateOptions templateOptions) {
    super(remoteId, providerId, swordId, cloud, owner, location);
    this.name = name;
    this.generatedLoginUsername = generatedLoginUsername;
    this.generatedLoginPassword = generatedLoginPassword;
    this.generatedPrivateKey = generatedPrivateKey;
    this.image = image;
    this.hardware = hardware;
    this.templateOptions = templateOptions;
  }

  public Optional<Image> image() {
    return Optional.ofNullable(image);
  }

  public Optional<Hardware> hardware() {
    return Optional.ofNullable(hardware);
  }

  public void addIpAddress(IpAddress ipAddress) {
    if (ipAddresses == null) {
      this.ipAddresses = new HashSet<>();
    }
    this.ipAddresses.add(ipAddress);
  }

  public void removeIpAddress(IpAddress ipAddress) {
    if (ipAddresses == null) {
      return;
    }
    ipAddresses.remove(ipAddress);
  }

  public void removeIpAddresses() {
    if (ipAddresses == null) {
      return;
    }
    ipAddresses.clear();
  }

  public Optional<IpAddress> publicIpAddress() {
    return ipAddresses.stream().filter(ipAddress -> ipAddress.getIpType().equals(IpType.PUBLIC))
        .findAny();
  }

  public Optional<IpAddress> privateIpAddress(boolean fallbackToPublic) {

    final Optional<IpAddress> any =
        ipAddresses.stream().filter(ipAddress -> ipAddress.getIpType().equals(IpType.PRIVATE))
            .findAny();

    if (!any.isPresent() && fallbackToPublic) {
      return publicIpAddress();
    }
    return any;
  }

  public String name() {
    return name;
  }


  public Optional<String> loginPrivateKey() {
    return Optional.ofNullable(generatedPrivateKey);
  }

  public int remotePort() {
    if (image == null) {
      throw new RemotePortProvider.UnknownRemotePortException(
          "Remote port is unknown as image is no longer known.");
    }
    return image.operatingSystem().operatingSystemFamily().remotePort();
  }

  public Optional<TemplateOptions> templateOptions() {
    return Optional.ofNullable(templateOptions);
  }

  public void setGeneratedLoginUsername(@Nullable String generatedLoginUsername) {
    if (this.generatedLoginUsername != null) {
      throw new IllegalStateException("Changing generatedLoginUsername not permitted.");
    }
    this.generatedLoginUsername = generatedLoginUsername;
  }

  public void setGeneratedLoginPassword(@Nullable String generatedLoginPassword) {
    if (this.generatedLoginPassword != null) {
      throw new IllegalStateException("Changing generatedLoginPassword not permitted.");
    }
    this.generatedLoginPassword = generatedLoginPassword;
  }

  public void setGeneratedPrivateKey(@Nullable String generatedPrivateKey) {
    if (this.generatedPrivateKey != null) {
      throw new IllegalStateException("Changing generatedPrivateKey not permitted.");
    }
    this.generatedPrivateKey = generatedPrivateKey;
  }

  public String loginName() {
    if (generatedLoginUsername != null) {
      return generatedLoginUsername;
    }
    if (image == null) {
      throw new UnknownLoginNameException(
          "Login name is unknown as image is not longer known.");
    }
    return image.loginName();
  }

  public Optional<String> loginPassword() {
    if (generatedLoginPassword != null) {
      return Optional.of(generatedLoginPassword);
    }
    if (image == null) {
      return Optional.empty();
    } else {
      return image.getLoginPasswordOverride();
    }
  }

  public List<Instance> instances() {
    if (instances == null) {
      return Collections.emptyList();
    }
    return instances;
  }

  public OperatingSystem operatingSystem() {
    if (image == null) {
      throw new IllegalStateException("Image is not longer known.");
    }
    return image.operatingSystem();
  }

  public void unbind() {
    unbindProviderIds();
    unbindRemoteId();
    removeIpAddresses();
    setRemoteState(RemoteState.INPROGRESS);
    generatedLoginUsername = null;
    generatedLoginPassword = null;
    generatedPrivateKey = null;
  }
}
