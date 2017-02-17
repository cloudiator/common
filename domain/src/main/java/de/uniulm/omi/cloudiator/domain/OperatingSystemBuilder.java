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

/**
 * Created by daniel on 08.03.16.
 */
public class OperatingSystemBuilder {

    private OperatingSystemArchitecture operatingSystemArchitecture;
    private OperatingSystemVersion operatingSystemVersion;
    private OperatingSystemFamily operatingSystemFamily;

    private OperatingSystemBuilder() {
    }

    public static OperatingSystemBuilder newBuilder() {
        return new OperatingSystemBuilder();
    }

    public OperatingSystemBuilder architecture(
        OperatingSystemArchitecture operatingSystemArchitecture) {
        this.operatingSystemArchitecture = operatingSystemArchitecture;
        return this;
    }

    public OperatingSystemBuilder version(OperatingSystemVersion operatingSystemVersion) {
        this.operatingSystemVersion = operatingSystemVersion;
        return this;
    }

    public OperatingSystemBuilder family(OperatingSystemFamily operatingSystemFamily) {
        this.operatingSystemFamily = operatingSystemFamily;
        return this;
    }

    public OperatingSystem build() {
        return new OperatingSystemImpl(operatingSystemFamily, operatingSystemArchitecture,
            operatingSystemVersion);
    }



}
