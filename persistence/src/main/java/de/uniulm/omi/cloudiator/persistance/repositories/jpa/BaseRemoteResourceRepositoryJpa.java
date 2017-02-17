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

import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import de.uniulm.omi.cloudiator.persistance.entities.RemoteResource;
import de.uniulm.omi.cloudiator.persistance.repositories.RemoteResourceRepository;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 21.06.15.
 */
@Deprecated class BaseRemoteResourceRepositoryJpa<T extends RemoteResource>
    extends BaseModelRepositoryJpa<T> implements RemoteResourceRepository<T> {

    @Inject
    public BaseRemoteResourceRepositoryJpa(Provider<EntityManager> entityManager, TypeLiteral<T> type) {
        super(entityManager, type);
    }

    @Nullable @Override public T findByRemoteId(String remoteId) {
        checkNotNull(remoteId);
        String queryString = String.format("from %s where remoteId=:remoteId", type.getName());
        Query query = em().createQuery(queryString).setParameter("remoteId", remoteId);
        try {
            //noinspection unchecked
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
