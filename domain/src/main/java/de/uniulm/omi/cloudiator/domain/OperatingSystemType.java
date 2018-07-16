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

  UNKNOWN(null, HomeDirFunctions.unknown(), RemoteType.UNKNOWN), UNIX(22, HomeDirFunctions.unix(),
      RemoteType.SSH), LINUX(22, HomeDirFunctions.unix(), RemoteType.SSH), WINDOWS(5985,
      HomeDirFunctions.windows(), RemoteType.WINRM), BSD(null, HomeDirFunctions.unknown(),
      RemoteType.SSH), MAC(null, HomeDirFunctions.unknown(), RemoteType.SSH);

  public static final OperatingSystemType DEFAULT = UNKNOWN;

  @Nullable
  private final Integer defaultRemotePort;
  private final HomeDirFunction homeDirFunction;
  private final RemoteType remoteType;

  OperatingSystemType(@Nullable Integer defaultRemotePort, HomeDirFunction homeDirFunction,
      RemoteType remoteType) {
    this.defaultRemotePort = defaultRemotePort;
    checkNotNull(homeDirFunction);
    this.homeDirFunction = homeDirFunction;
    checkNotNull(remoteType);
    this.remoteType = remoteType;
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
}
