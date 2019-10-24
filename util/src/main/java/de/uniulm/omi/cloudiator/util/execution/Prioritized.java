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

package de.uniulm.omi.cloudiator.util.execution;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

/**
 * A prioritized object, offering a way to compare two objects with respect to their priority. <p>
 * For this purpose this class extends {@link Comparable} and offers a default implementation of the
 * compareTo method.
 */
public interface Prioritized extends Comparable<Prioritized> {

  int getPriority();

  @Override
  default int compareTo(@Nonnull Prioritized o) {
    checkNotNull(o);
    return Integer.compare(o.getPriority(), getPriority());
  }

  class Priority {

    public static final int HIGH = 2;
    public static final int MEDIUM = 1;
    public static final int LOW = 0;

    private Priority() {
      throw new AssertionError("Intentionally left empty.");
    }
  }
}
