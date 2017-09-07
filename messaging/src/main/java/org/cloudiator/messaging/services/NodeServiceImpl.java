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
import org.cloudiator.messages.Node.NodeRequestMessage;
import org.cloudiator.messages.Node.NodeRequestResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

public class NodeServiceImpl implements NodeService {

  private final MessageInterface messageInterface;
  private long timeout = 0;

  @Inject
  public void setResponseTimeout(@Named("responseTimeout") long timeout) {
    this.timeout = timeout;
  }

  @Inject
  public NodeServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public NodeRequestResponse createNodes(NodeRequestMessage nodeRequestMessage)
      throws ResponseException {
    return messageInterface.call(nodeRequestMessage, NodeRequestResponse.class, timeout);
  }

  @Override
  public void createNodesAsync(NodeRequestMessage nodeRequestMessage,
      ResponseCallback<NodeRequestResponse> callback) {
    messageInterface.callAsync(nodeRequestMessage, NodeRequestResponse.class, callback);
  }


}
