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

import java.util.SortedSet;

/**
 * Created by daniel on 09.03.16.
 */
public class OperatingSystemVersionFormats {

    private OperatingSystemVersionFormats() {
        throw new AssertionError();
    }

    public static OperatingSystemVersionFormat set(
        SortedSet<OperatingSystemVersion> possibleValues) {
        return new SetBasedOperatingSystemFormat(possibleValues);
    }

    public static OperatingSystemVersionFormat unknown() {
        return new UnknownOperatingSystemFormat();
    }

}
