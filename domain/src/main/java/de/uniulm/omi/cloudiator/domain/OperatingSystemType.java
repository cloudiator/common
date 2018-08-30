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
import javax.annotation.Nullable;

/**
 * Created by daniel on 08.03.16.
 */
public enum OperatingSystemType implements RemotePortProvider, HomeDirFunctionProvider {

  UNKNOWN(null, HomeDirFunctions.unknown(), RemoteType.UNKNOWN, false), UNIX(22,
      HomeDirFunctions.unix(),
      RemoteType.SSH, false), LINUX(22, HomeDirFunctions.unix(), RemoteType.SSH, true), WINDOWS(
      5985,
      HomeDirFunctions.windows(), RemoteType.WINRM, false), BSD(null, HomeDirFunctions.unknown(),
      RemoteType.SSH, false), MAC(null, HomeDirFunctions.unknown(), RemoteType.SSH, false);

  public static final OperatingSystemType DEFAULT = UNKNOWN;

  @Nullable
  private final Integer defaultRemotePort;
  private final HomeDirFunction homeDirFunction;
  private final RemoteType remoteType;
  private final boolean supportsDocker;

  OperatingSystemType(@Nullable Integer defaultRemotePort, HomeDirFunction homeDirFunction,
      RemoteType remoteType, boolean supportsDocker) {
    this.defaultRemotePort = defaultRemotePort;
    checkNotNull(homeDirFunction);
    this.homeDirFunction = homeDirFunction;
    checkNotNull(remoteType);
    this.remoteType = remoteType;
    this.supportsDocker = supportsDocker;
  }

  @Override
  public int remotePort() {
    if (defaultRemotePort == null) {
      throw new UnknownRemotePortException(
          "No remote port defined for operating system type " + this);
    }
    return defaultRemotePort;
  }

  @Override
  public HomeDirFunction homeDirFunction() {
    return homeDirFunction;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("defaultRemotePort", defaultRemotePort)
        .add("homeDirFunction", homeDirFunction).toString();
  }

  public RemoteType remoteType() {
    return remoteType;
  }

  public boolean supportsDocker() {
    return supportsDocker;
  }
}
