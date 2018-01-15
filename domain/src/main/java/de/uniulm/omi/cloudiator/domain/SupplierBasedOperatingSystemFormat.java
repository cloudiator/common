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

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by daniel on 09.03.16.
 */
public class SupplierBasedOperatingSystemFormat implements OperatingSystemVersionFormat {

  private final SortedSet<OperatingSystemVersion> possibleValues;

  SupplierBasedOperatingSystemFormat(
      Supplier<Set<OperatingSystemVersion>> operatingSystemVersionsSupplier) {
    this.possibleValues = new TreeSet<>(operatingSystemVersionsSupplier.get());
  }

  @Override
  public boolean isValid(OperatingSystemVersion operatingSystemVersion) {
    return possibleValues.contains(operatingSystemVersion);
  }

  @Override
  public OperatingSystemVersion newest() {
    return possibleValues.last();
  }

  @Override
  public Set<OperatingSystemVersion> allVersions() {
    return ImmutableSet.copyOf(possibleValues);
  }

  @Override
  public OperatingSystemVersion parse(String version) {
    final List<OperatingSystemVersion> collect = possibleValues.stream().filter(
        operatingSystemVersion -> operatingSystemVersion.name().isPresent()
            && operatingSystemVersion.name().get().equals(version))
        .collect(Collectors.toList());
    if (collect.size() != 1) {
      throw new IllegalArgumentException();
    }
    return collect.get(0);
  }
}
