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

package de.uniulm.omi.cloudiator.util;


import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;

public class StreamUtil {

  private StreamUtil() {
    throw new AssertionError("Do not instantiate");
  }

  /**
   * Can be replaced with MoreCollectors.onlyElement as soon as we can upgrade to the newest Guava
   * version.
   *
   * @param <E> type of the element.
   * @return only one element.
   * @see <a href="https://stackoverflow.com/questions/22694884/filter-java-stream-to-1-and-only-1-element"/>
   */
  public static <E> Collector<E, ?, Optional<E>> getOnly() {
    return Collector.of(
        AtomicReference<E>::new,
        (ref, e) -> {
          if (!ref.compareAndSet(null, e)) {
            throw new IllegalArgumentException("Multiple values");
          }
        },
        (ref1, ref2) -> {
          if (ref1.get() == null) {
            return ref2;
          } else if (ref2.get() != null) {
            throw new IllegalArgumentException("Multiple values");
          } else {
            return ref1;
          }
        },
        ref -> Optional.ofNullable(ref.get()),
        Collector.Characteristics.UNORDERED);
  }


}
