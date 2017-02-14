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

import de.uniulm.omi.cloudiator.domain.OperatingSystemVersion;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by daniel on 09.03.16.
 */
public class OperatingSystemVersionFormats {

    private OperatingSystemVersionFormats() {
        throw new AssertionError();
    }

    public static OperatingSystemVersionFormat unknown() {
        return new UnknownOperatingSystemFormat();
    }

    public static OperatingSystemVersionFormat set(Set<OperatingSystemVersion> set) {
        return new SupplierBasedOperatingSystemFormat(() -> set);
    }

    public static OperatingSystemVersionFormat set(int... versions) {
        return new SupplierBasedOperatingSystemFormat(() -> Arrays.stream(versions)
            .mapToObj(i -> OperatingSystemVersions.of(i, String.valueOf(i)))
            .collect(Collectors.toSet()));
    }

    public static OperatingSystemVersionFormat supplier(
        Supplier<Set<OperatingSystemVersion>> supplier) {
        return new SupplierBasedOperatingSystemFormat(supplier);
    }

}
