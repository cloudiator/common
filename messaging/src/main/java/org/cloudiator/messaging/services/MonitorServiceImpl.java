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
import org.cloudiator.messages.Monitor.CreateMonitorRequest;
import org.cloudiator.messages.Monitor.DeleteMonitorRequest;
import org.cloudiator.messages.Monitor.MonitorCreatedResponse;
import org.cloudiator.messages.Monitor.MonitorDeletedResponse;
import org.cloudiator.messages.Monitor.MonitorQueryRequest;
import org.cloudiator.messages.Monitor.MonitorQueryResponse;
import org.cloudiator.messages.Monitor.MonitorUpdatedResponse;
import org.cloudiator.messages.Monitor.UpdateMonitorRequest;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 23.06.17.
 */
public class MonitorServiceImpl implements MonitorService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  MonitorServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public MonitorQueryResponse findMonitors(MonitorQueryRequest MonitorQueryRequest)
      throws ResponseException {
    return messageInterface.call(MonitorQueryRequest, MonitorQueryResponse.class);
  }

  @Override
  public MonitorCreatedResponse addMonitor(CreateMonitorRequest createMonitorRequest)
      throws ResponseException {
    return messageInterface.call(createMonitorRequest, MonitorCreatedResponse.class, timeout);
  }

  @Override
  public MonitorUpdatedResponse getMonitor(UpdateMonitorRequest updateMonitorRequest)
      throws ResponseException {
    return messageInterface.call(updateMonitorRequest, MonitorUpdatedResponse.class, timeout);
  }

  @Override
  public MonitorDeletedResponse deleteMonitor(DeleteMonitorRequest deleteMonitorRequest)
      throws ResponseException {
    return messageInterface.call(deleteMonitorRequest, MonitorDeletedResponse.class);
  }
}
