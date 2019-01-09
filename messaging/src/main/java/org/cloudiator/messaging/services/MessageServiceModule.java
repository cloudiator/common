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

package org.cloudiator.messaging.services;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by daniel on 13.06.17.
 */
public class MessageServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CloudService.class).to(CloudServiceImpl.class).in(Singleton.class);
    bind(HardwareService.class).to(HardwareServiceImpl.class).in(Singleton.class);
    bind(ImageService.class).to(ImageServiceImpl.class).in(Singleton.class);
    bind(LocationService.class).to(LocationServiceImpl.class).in(Singleton.class);
    bind(VirtualMachineService.class).to(VirtualMachineServiceImpl.class).in(Singleton.class);
    bind(JobService.class).to(JobServiceImpl.class).in(Singleton.class);
    bind(TaskService.class).to(TaskServiceImpl.class).in(Singleton.class);
    bind(NodeService.class).to(NodeServiceImpl.class).in(Singleton.class);
    bind(MatchmakingService.class).to(MatchmakingServiceImpl.class).in(Singleton.class);
    bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
    bind(InstallationRequestService.class).to(InstallationRequestServiceImpl.class)
        .in(Singleton.class);
    bind(ProcessService.class).to(ProcessServiceImpl.class);
    bind(EncryptionService.class).to(EncryptionServiceImpl.class).in(Singleton.class);
    bind(SecureStoreService.class).to(SecureStoreServiceImpl.class).in(Singleton.class);
    bind(MonitorService.class).to(MonitorServiceImpl.class).in(Singleton.class);
    bind(FunctionService.class).to(FunctionServiceImpl.class).in(Singleton.class);
  }

}
