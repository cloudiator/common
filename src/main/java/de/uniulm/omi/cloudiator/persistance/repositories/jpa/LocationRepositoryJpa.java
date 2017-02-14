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

package de.uniulm.omi.cloudiator.persistance.repositories.jpa;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import de.uniulm.omi.cloudiator.persistance.entities.Location;
import de.uniulm.omi.cloudiator.persistance.repositories.LocationRepository;

import javax.persistence.EntityManager;

/**
 * Created by bwpc on 09.12.2014.
 */
class LocationRepositoryJpa extends BaseRemoteResourceRepositoryJpa<Location>
    implements LocationRepository {


    @Inject public LocationRepositoryJpa(Provider<EntityManager> entityManager, TypeLiteral<Location> type) {
        super(entityManager, type);
    }
}
