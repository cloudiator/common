/*
 * Copyright 2016 University of Ulm
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

package de.uniulm.omi.cloudiator.common.os;

import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.03.16.
 */
public enum OperatingSystemType implements RemotePortProvider, HomeDirFunctionProvider {

    UNKNOWN(null, null),
    UNIX(22, HomeDirFunctions.unix()),
    LINUX(22, HomeDirFunctions.unix()),
    WINDOWS(5985, HomeDirFunctions.windows()),
    BSD(null, null),
    MAC(null, null);

    public static final OperatingSystemType DEFAULT = UNKNOWN;

    @Nullable private final Integer defaultRemotePort;
    @Nullable private final HomeDirFunction homeDirFunction;

    OperatingSystemType(@Nullable Integer defaultRemotePort,
        @Nullable HomeDirFunction homeDirFunction) {
        this.defaultRemotePort = defaultRemotePort;
        checkNotNull(homeDirFunction);
        this.homeDirFunction = homeDirFunction;
    }

    @Override public int remotePort() {
        if (defaultRemotePort == null) {
            throw new UnknownRemotePortException(
                "No remote port defined for operating system type " + this);
        }
        return defaultRemotePort;
    }

    @Override public HomeDirFunction homeDirFunction() {
        if (homeDirFunction == null) {
            throw new UnknownHomeDirFunctionException(
                "Home directory function is unknown for operating system type " + this);
        }
        return homeDirFunction;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("defaultRemotePort", defaultRemotePort)
            .add("homeDirFunction", homeDirFunction).toString();
    }
}
