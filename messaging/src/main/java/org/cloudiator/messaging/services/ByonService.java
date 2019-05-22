package org.cloudiator.messaging.services;

import org.cloudiator.messages.Byon.AddByonNodeRequest;
import org.cloudiator.messages.Byon.ByonNodeAddedResponse;
import org.cloudiator.messages.Byon.ByonNodeAllocateRequestMessage;
import org.cloudiator.messages.Byon.ByonNodeAllocatedResponse;
import org.cloudiator.messages.Byon.ByonNodeDeleteRequestMessage;
import org.cloudiator.messages.Byon.ByonNodeDeletedResponse;
import org.cloudiator.messages.Byon.ByonNodeRemovedResponse;
import org.cloudiator.messages.Byon.RemoveByonNodeRequest;
import org.cloudiator.messaging.ResponseCallback;

public interface ByonService {
  void createByonPersistAllocAsync(ByonNodeAllocateRequestMessage byonNodeAllocateRequestMessage,
      ResponseCallback<ByonNodeAllocatedResponse> callback);
  void createByonPersistDelAsync(ByonNodeDeleteRequestMessage byonNodeDeleteRequestMessage,
      ResponseCallback<ByonNodeDeletedResponse> callback);
  void addByonNodeAsync(AddByonNodeRequest addByonNodeRequest,
      ResponseCallback<ByonNodeAddedResponse> callback);
  void removeByonNodeAsync(RemoveByonNodeRequest removeByonNodeRequest,
      ResponseCallback<ByonNodeRemovedResponse> callback);
}
