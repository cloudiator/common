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
import de.uniulm.omi.cloudiator.persistance.entities.FrontendUser;
import de.uniulm.omi.cloudiator.persistance.repositories.FrontendUserRepository;

import javax.persistence.EntityManager;

import static de.uniulm.omi.cloudiator.persistance.util.JpaResultHelper.getSingleResultOrNull;


/**
 * Created by daniel on 03.11.14.
 */
class FrontendUserRepositoryJpa extends BaseModelRepositoryJpa<FrontendUser>
    implements FrontendUserRepository {

    @Inject public FrontendUserRepositoryJpa(Provider<EntityManager> entityManager, TypeLiteral<FrontendUser> type) {
        super(entityManager, type);
    }

    @Override public FrontendUser findByMail(final String mail) {
        return (FrontendUser) getSingleResultOrNull(
            em().createQuery("from FrontendUser fu where mail = :mail").setParameter("mail", mail));
    }
}
