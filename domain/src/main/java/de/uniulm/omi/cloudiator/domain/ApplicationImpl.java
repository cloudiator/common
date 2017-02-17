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

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 13.02.17.
 */
public class ApplicationImpl implements Application {

    private final Set<ApplicationComponent> applicationComponents;
    private final Set<Communication> communications;
    private final String name;

    ApplicationImpl(String name, Set<ApplicationComponent> applicationComponents,
        Set<Communication> communications) {

        checkNotNull(name, "name is null");
        checkArgument(!name.isEmpty(), "name is empty");
        checkNotNull(applicationComponents, "applicationComponents is null");
        checkNotNull(communications, "communications is null");

        this.name = name;
        this.applicationComponents = ImmutableSet.copyOf(applicationComponents);
        this.communications = ImmutableSet.copyOf(communications);
        validateCommunication();
    }

    private boolean validateCommunication() {
        //todo
        return true;
    }

    @Override public Set<ApplicationComponent> applicationComponents() {
        return applicationComponents;
    }

    @Override public Set<Communication> communications() {
        return communications;
    }

    @Override public String name() {
        return name;
    }
}
