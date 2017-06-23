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

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.Application.ApplicationCreatedResponse;
import org.cloudiator.messages.Application.ApplicationDeletedResponse;
import org.cloudiator.messages.Application.ApplicationQueryRequest;
import org.cloudiator.messages.Application.ApplicationQueryResponse;
import org.cloudiator.messages.Application.ApplicationUpdatedResponse;
import org.cloudiator.messages.Application.CreateApplicationRequest;
import org.cloudiator.messages.Application.DeleteApplicationRequest;
import org.cloudiator.messages.Application.UpdateApplicationRequest;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 23.06.17.
 */
public class ApplicationServiceImpl implements ApplicationService {

  private final MessageInterface messageInterface;
  private long timeout = 0;

  @Inject
  public ApplicationServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Inject
  public void setResponseTimeout(@Named("responseTimeout") long timeout) {
    this.timeout = timeout;
  }

  @Override
  public ApplicationQueryResponse getApplications(ApplicationQueryRequest applicationQueryRequest)
      throws ResponseException {
    return messageInterface.call(applicationQueryRequest, ApplicationQueryResponse.class, timeout);
  }

  @Override
  public ApplicationCreatedResponse createApplication(
      CreateApplicationRequest createApplicationRequest) throws ResponseException {
    return messageInterface
        .call(createApplicationRequest, ApplicationCreatedResponse.class, timeout);
  }

  @Override
  public ApplicationUpdatedResponse updateApplication(
      UpdateApplicationRequest updateApplicationRequest) throws ResponseException {
    return messageInterface
        .call(updateApplicationRequest, ApplicationUpdatedResponse.class, timeout);
  }

  @Override
  public ApplicationDeletedResponse deleteApplication(
      DeleteApplicationRequest deleteApplicationRequest) throws ResponseException {
    return messageInterface
        .call(deleteApplicationRequest, ApplicationDeletedResponse.class, timeout);
  }
}
