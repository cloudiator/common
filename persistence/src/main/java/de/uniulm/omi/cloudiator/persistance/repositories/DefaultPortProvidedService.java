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

package de.uniulm.omi.cloudiator.persistance.repositories;

import com.google.inject.Inject;

import java.util.Set;
import java.util.stream.Collectors;

import de.uniulm.omi.cloudiator.persistance.entities.PortProvided;

/**
 * Created by daniel on 15.03.16.
 */
@Deprecated public class DefaultPortProvidedService extends BaseModelService<PortProvided>
    implements PortProvidedService {

    @Inject public DefaultPortProvidedService(ModelRepository<PortProvided> modelRepository) {
        super(modelRepository);
    }

    @Override public Set<Integer> allProvidedPorts() {
        return getAll().stream().map(PortProvided::getPort).collect(Collectors.toSet());
    }
}
