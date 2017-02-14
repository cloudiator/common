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
import de.uniulm.omi.cloudiator.persistance.entities.RemoteResource;

/**
 * Created by daniel on 21.06.15.
 */
@Deprecated public class BaseRemoteModelService<T extends RemoteResource>
    extends BaseModelService<T> implements RemoteModelService<T> {

    protected final RemoteResourceRepository<T> tRemoteResourceRepository;

    @Inject public BaseRemoteModelService(RemoteResourceRepository<T> tRemoteResourceRepository) {
        super(tRemoteResourceRepository);
        this.tRemoteResourceRepository = tRemoteResourceRepository;
    }

    @Override public T getByRemoteId(String remoteId) {
        return tRemoteResourceRepository.findByRemoteId(remoteId);
    }
}
