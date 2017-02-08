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

import de.uniulm.omi.cloudiator.domain.OperatingSystem;
import de.uniulm.omi.cloudiator.domain.OperatingSystemArchitecture;
import de.uniulm.omi.cloudiator.domain.OperatingSystemFamily;

/**
 * Created by daniel on 29.07.16.
 */
public class OperatingSystems {

    public OperatingSystems() {
        throw new AssertionError("static class");
    }

    public static OperatingSystem unknown() {
        return OperatingSystemBuilder.newBuilder().architecture(OperatingSystemArchitecture.UNKNOWN)
            .family(OperatingSystemFamily.UNKNOWN).version(OperatingSystemVersionImpl.unknown())
            .build();
    }

}
