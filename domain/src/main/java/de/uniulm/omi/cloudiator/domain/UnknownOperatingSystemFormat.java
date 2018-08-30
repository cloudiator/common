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
import java.util.Set;

/**
 * Created by daniel on 09.03.16.
 */
public class UnknownOperatingSystemFormat implements OperatingSystemVersionFormat {

  @Override
  public boolean isValid(OperatingSystemVersion operatingSystemVersion) {
    return false;
  }

  @Deprecated
  @Override
  public OperatingSystemVersion newest() {
    return OperatingSystemVersions.unknown();
  }

  @Override
  public Set<OperatingSystemVersion> allVersions() {
    return Collections.emptySet();
  }

  @Deprecated
  @Override
  public OperatingSystemVersion parse(String version) {
    return OperatingSystemVersions.unknown();
  }
}
