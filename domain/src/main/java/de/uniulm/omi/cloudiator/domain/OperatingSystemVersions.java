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

import java.util.Collections;

/**
 * Created by daniel on 14.02.17.
 */
public class OperatingSystemVersions {

  private OperatingSystemVersions() {
  }

  public static OperatingSystemVersion unknown() {
    return new OperatingSystemVersionImpl(null, null,
        Collections.emptySet());
  }

  public static OperatingSystemVersion of(int version, String name) {
    return new OperatingSystemVersionImpl(version, name, Collections.emptySet());
  }

  public static OperatingSystemVersion of(int version,
      OperatingSystemVersionFormat operatingSystemVersionFormat) {
    return operatingSystemVersionFormat.parse(version);
  }
}
