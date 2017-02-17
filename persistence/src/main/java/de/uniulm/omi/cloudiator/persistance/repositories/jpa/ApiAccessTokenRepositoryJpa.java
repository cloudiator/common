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
import de.uniulm.omi.cloudiator.persistance.entities.ApiAccessToken;
import de.uniulm.omi.cloudiator.persistance.repositories.ApiAccessTokenRepository;

import javax.persistence.EntityManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 19.12.14.
 */
class ApiAccessTokenRepositoryJpa extends BaseModelRepositoryJpa<ApiAccessToken>
    implements ApiAccessTokenRepository {

    @Inject public ApiAccessTokenRepositoryJpa(Provider<EntityManager> entityManager,
        TypeLiteral<ApiAccessToken> type) {
        super(entityManager, type);
    }

    @Override public ApiAccessToken findByToken(String token) {
        checkNotNull(token);
        //noinspection unchecked
        return (ApiAccessToken) em().createQuery("from ApiAccessToken where token = :token")
            .setParameter("token", token).getResultList().stream().findFirst().orElse(null);
    }

    @Override public void deleteExpiredTokens(Long deadline) {
        em().createQuery("delete from ApiAccessToken where expiresAt < :deadline")
            .setParameter("deadline", deadline).executeUpdate();
    }
}
