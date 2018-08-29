/*
 * Copyright 2018 University of Ulm
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

import static com.google.common.base.Preconditions.checkState;

public class DockerHubUbuntuImagePath implements DockerHubImagePath {

  private final static String UBUNTU_DOCKERHUB_REPO = "ubuntu";

  @Override
  public String generateImagePath(OperatingSystemVersion operatingSystemVersion) {

    checkState(operatingSystemVersion.name().isPresent(), "Name of the version is not present.");
    return(UBUNTU_DOCKERHUB_REPO + ":" + operatingSystemVersion.name());
  }
}
