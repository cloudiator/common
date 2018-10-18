/*
 * Copyright 2018 University of Ulm
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

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class DockerHubStringTest {

  @Test
  public void test() {
    OperatingSystemFamily operatingSystemFamilyU = OperatingSystemFamily.UBUNTU;
    OperatingSystemArchitecture operatingSystemArchitectureU = OperatingSystemArchitecture.AMD64;
    Set<String> altNamesU = new HashSet<>();
    altNamesU.add("16.04");
    OperatingSystemVersion versionU = new OperatingSystemVersionImpl(1604, "16.04", altNamesU);
    OperatingSystemImpl implU = new OperatingSystemImpl(operatingSystemFamilyU, operatingSystemArchitectureU, versionU);
    System.out.println(implU.getDockerHubImagePath());

    OperatingSystemFamily operatingSystemFamilyC = OperatingSystemFamily.CENTOS;
    OperatingSystemArchitecture operatingSystemArchitectureC = OperatingSystemArchitecture.AMD64;
    Set<String> altNamesC = new HashSet<>();
    altNamesC.add("7");
    OperatingSystemVersion versionC = new OperatingSystemVersionImpl(7, "7", altNamesC);
    OperatingSystemImpl implC = new OperatingSystemImpl(operatingSystemFamilyC, operatingSystemArchitectureC, versionC);
    System.out.println(implC.getDockerHubImagePath());
  }
}
