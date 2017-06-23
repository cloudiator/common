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
import org.cloudiator.messages.Component.ComponentCreatedResponse;
import org.cloudiator.messages.Component.ComponentDeletedResponse;
import org.cloudiator.messages.Component.ComponentQueryRequest;
import org.cloudiator.messages.Component.ComponentQueryResponse;
import org.cloudiator.messages.Component.ComponentUpdatedResponse;
import org.cloudiator.messages.Component.CreateComponentRequest;
import org.cloudiator.messages.Component.DeleteComponentRequest;
import org.cloudiator.messages.Component.UpdateComponentRequest;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 23.06.17.
 */
public class ComponentServiceImpl implements ComponentService {

  private final MessageInterface messageInterface;
  private long timeout = 0;

  @Inject
  public ComponentServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Inject
  public void setResponseTimeout(@Named("responseTimeout") long timeout) {
    this.timeout = timeout;
  }

  @Override
  public ComponentQueryResponse getComponents(ComponentQueryRequest componentQueryRequest)
      throws ResponseException {
    return messageInterface.call(componentQueryRequest, ComponentQueryResponse.class, timeout);
  }

  @Override
  public ComponentCreatedResponse createComponent(CreateComponentRequest createComponentRequest)
      throws ResponseException {
    return messageInterface.call(createComponentRequest, ComponentCreatedResponse.class, timeout);
  }

  @Override
  public ComponentUpdatedResponse updateComponent(UpdateComponentRequest updateComponentRequest)
      throws ResponseException {
    return messageInterface.call(updateComponentRequest, ComponentUpdatedResponse.class, timeout);
  }

  @Override
  public ComponentDeletedResponse deleteComponent(DeleteComponentRequest deleteComponentRequest)
      throws ResponseException {
    return messageInterface.call(deleteComponentRequest, ComponentDeletedResponse.class, timeout);
  }
}
