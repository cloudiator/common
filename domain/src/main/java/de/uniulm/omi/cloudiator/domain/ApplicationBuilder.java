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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 13.02.17.
 */
public class ApplicationBuilder {

    private String name;
    private Set<ApplicationComponent> applicationComponents = new HashSet<>();
    private Set<Communication> communications = new HashSet<>();

    private ApplicationBuilder() {

    }

    public ApplicationBuilder newBuilder() {
        return new ApplicationBuilder();
    }

    public ApplicationBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ApplicationBuilder addApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponents.add(applicationComponent);
        return this;
    }

    public ApplicationBuilder addApplicationComponents(
        Collection<? extends ApplicationComponent> applicationComponents) {
        this.applicationComponents.addAll(applicationComponents);
        return this;
    }

    public ApplicationBuilder addCommunication(Communication communication) {
        this.communications.add(communication);
        return this;
    }

    public ApplicationBuilder addCommunications(Set<? extends Communication> communications) {
        this.communications.addAll(communications);
        return this;
    }

    public Application build() {
        return new ApplicationImpl(name, applicationComponents, communications);
    }


}
