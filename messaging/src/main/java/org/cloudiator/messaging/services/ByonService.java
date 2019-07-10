package org.cloudiator.messaging.services;

import org.cloudiator.messages.Byon.AddByonNodeRequest;
import org.cloudiator.messages.Byon.ByonNodeAddedResponse;
import org.cloudiator.messages.Byon.ByonNodeAllocateRequestMessage;
import org.cloudiator.messages.Byon.ByonNodeAllocatedResponse;
import org.cloudiator.messages.Byon.ByonNodeDeleteRequestMessage;
import org.cloudiator.messages.Byon.ByonNodeDeletedResponse;
import org.cloudiator.messages.Byon.ByonNodeEvent;
import org.cloudiator.messages.Byon.ByonNodeQueryRequest;
import org.cloudiator.messages.Byon.ByonNodeQueryResponse;
import org.cloudiator.messages.Byon.ByonNodeRemovedResponse;
import org.cloudiator.messages.Byon.RemoveByonNodeRequest;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

public interface ByonService {
  void createByonPersistAllocAsync(ByonNodeAllocateRequestMessage byonNodeAllocateRequestMessage,
      ResponseCallback<ByonNodeAllocatedResponse> callback);
  void createByonPersistDelAsync(ByonNodeDeleteRequestMessage byonNodeDeleteRequestMessage,
      ResponseCallback<ByonNodeDeletedResponse> callback);
  ByonNodeAddedResponse addByonNode(AddByonNodeRequest addByonNodeRequest)
      throws ResponseException;
  void removeByonNodeAsync(RemoveByonNodeRequest removeByonNodeRequest,
      ResponseCallback<ByonNodeRemovedResponse> callback);
  ByonNodeQueryResponse findByonNodes(ByonNodeQueryRequest byonQueryRequest)
      throws ResponseException;
  void subscribeByonNodeEvents(MessageCallback<ByonNodeEvent> callback);
}
