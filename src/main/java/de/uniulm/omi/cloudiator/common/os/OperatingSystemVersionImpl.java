/*
 *  Copyright 2016 University of Ulm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.uniulm.omi.cloudiator.common.os;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.domain.OperatingSystemVersion;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.03.16.
 */
public class OperatingSystemVersionImpl
    implements Comparable<OperatingSystemVersion>, OperatingSystemVersion {

    public static final int UNKNOWN_VERSION = -1;
    private final int version;
    @Nullable private final String name;
    private final Set<String> alternativeNames;

    private OperatingSystemVersionImpl(int version, @Nullable String name,
        Set<String> alternativeNames) {
        checkArgument((version > 0) || (version == UNKNOWN_VERSION),
            "Version must be greater than 0 or equal UNKNOWN_VERSION");
        this.version = version;
        this.name = name;
        checkNotNull(alternativeNames);
        this.alternativeNames = alternativeNames;
    }

    public static OperatingSystemVersion of(int version, String name) {
        return new OperatingSystemVersionImpl(version, name, Collections.emptySet());
    }

    public static OperatingSystemVersion of(String name,
        OperatingSystemVersionFormat operatingSystemVersionFormat) {
        return operatingSystemVersionFormat.parse(name);
    }

    public static OperatingSystemVersion unknown() {
        return new OperatingSystemVersionImpl(UNKNOWN_VERSION, null, Collections.emptySet());
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OperatingSystemVersionImpl that = (OperatingSystemVersionImpl) o;

        return version == that.version;
    }

    @Override public int hashCode() {
        return version;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("version", version).add("name", name)
            .toString();
    }

    @Override public int compareTo(OperatingSystemVersion operatingSystemVersion) {
        checkNotNull(operatingSystemVersion);
        return Integer.valueOf(version).compareTo(operatingSystemVersion.version());
    }

    @Override @JsonProperty public Optional<String> name() {
        return Optional.ofNullable(name);
    }

    @Override @JsonProperty public int version() {
        return version;
    }



}
