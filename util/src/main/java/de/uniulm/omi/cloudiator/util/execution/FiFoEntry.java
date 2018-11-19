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

import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nonnull;

/**
 * A FIFO Entry. <p> Wraps a comparable to offer a tie-breaker if the {@link Comparable} returns 0
 * (equal). <p> Implementation is taken from the suggestions given in {@link
 * java.util.concurrent.PriorityBlockingQueue}
 *
 * @param <E> the object to wrap.
 */
class FiFoEntry<E extends Comparable<? super E>> implements Comparable<FiFoEntry<E>> {

  private static final AtomicLong seq = new AtomicLong(0);
  private final long seqNum;
  private final E entry;

  private FiFoEntry(E entry) {
    seqNum = seq.getAndIncrement();
    this.entry = entry;
  }

  static <E extends Comparable<? super E>> FiFoEntry<E> of(E entry) {
    return new FiFoEntry<>(entry);
  }

  public E getEntry() {
    return entry;
  }

  @Override
  public int compareTo(@Nonnull FiFoEntry<E> other) {
    checkNotNull(other);
    int res = entry.compareTo(other.entry);
    if (res == 0 && other.entry != this.entry) {
      if (seqNum < other.seqNum) {
        res = -1;
      } else {
        res = 1;
      }
    }
    return res;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FiFoEntry<?> fiFoEntry = (FiFoEntry<?>) o;

    if (seqNum != fiFoEntry.seqNum) {
      return false;
    }
    //noinspection EqualsBetweenInconvertibleTypes
    return entry.equals(fiFoEntry.entry);

  }

  @Override
  public int hashCode() {
    int result = (int) (seqNum ^ (seqNum >>> 32));
    result = 31 * result + (entry != null ? entry.hashCode() : 0);
    return result;
  }
}
