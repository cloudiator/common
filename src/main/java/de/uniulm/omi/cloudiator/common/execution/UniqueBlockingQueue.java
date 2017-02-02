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

package de.uniulm.omi.cloudiator.common.execution;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by daniel on 08.05.15.
 */
public class UniqueBlockingQueue<T> implements SimpleBlockingQueue<T> {

    private final Set<T> set;
    private final SimpleBlockingQueue<T> queue;

    public UniqueBlockingQueue(SimpleBlockingQueue<T> queue) {
        set = Sets.newConcurrentHashSet();
        this.queue = queue;
    }

    @Override public void add(T t) {
        if (this.set.add(t)) {
            this.queue.add(t);
        }
    }

    @Override public T take() throws InterruptedException {
        T t = this.queue.take();
        this.set.remove(t);
        return t;
    }

}
