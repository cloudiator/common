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

package de.uniulm.omi.cloudiator.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.net.URL;
import java.util.Optional;

/**
 * Created by daniel on 08.03.16.
 */
public class OperatingSystemImpl implements OperatingSystem, Serializable {

  private static final long serialVersionUID = 6899219972636493921L;
  private OperatingSystemFamily operatingSystemFamily;
  private OperatingSystemArchitecture operatingSystemArchitecture;
  private OperatingSystemVersion version;


  public OperatingSystemImpl(OperatingSystemFamily operatingSystemFamily,
      OperatingSystemArchitecture operatingSystemArchitecture, OperatingSystemVersion version) {

    checkNotNull(operatingSystemFamily, "operatingSystemFamily is null");
    checkNotNull(operatingSystemArchitecture, "operatingSystemArchitecture is null");
    checkNotNull(version, "version is null");

    this.operatingSystemFamily = operatingSystemFamily;
    this.operatingSystemArchitecture = operatingSystemArchitecture;
    this.version = version;
  }

  @Override
  public OperatingSystemFamily operatingSystemFamily() {
    return operatingSystemFamily;
  }

  @Override
  public OperatingSystemArchitecture operatingSystemArchitecture() {
    return operatingSystemArchitecture;
  }

  @Override
  public OperatingSystemVersion operatingSystemVersion() {
    return version;
  }

  @Override
  public Optional<String> dockerImage() {
    if (!operatingSystemFamily.dockerImageFunction().isPresent()) {
      return Optional.empty();
    }

    return Optional.ofNullable(operatingSystemFamily.dockerImageFunction().get().apply(version));
  }

  @Override
  public Optional<URL> downloadUrl(ImageFormat imageFormat) {

    if (!operatingSystemFamily.downloadUrlFunction().isPresent()) {
      return Optional.empty();
    }

    return Optional.ofNullable(operatingSystemFamily.downloadUrlFunction().get()
        .generateURL(operatingSystemArchitecture, version, imageFormat));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OperatingSystemImpl)) {
      return false;
    }

    OperatingSystemImpl that = (OperatingSystemImpl) o;

    if (operatingSystemFamily != that.operatingSystemFamily) {
      return false;
    }
    if (operatingSystemArchitecture != that.operatingSystemArchitecture) {
      return false;
    }
    return version.equals(that.version);

  }

  @Override
  public int hashCode() {
    int result = operatingSystemFamily.hashCode();
    result = 31 * result + operatingSystemArchitecture.hashCode();
    result = 31 * result + version.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("family", operatingSystemFamily)
        .add("arch", operatingSystemArchitecture).add("version", version).toString();
  }

  @Override
  public String loginName() {
    return operatingSystemFamily.loginName();
  }

  @Override
  public int remotePort() {
    return operatingSystemFamily.remotePort();
  }
}
