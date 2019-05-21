package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import org.cloudiator.messages.Byon.ByonNodeAllocateRequestMessage;
import org.cloudiator.messages.Byon.ByonNodeAllocatedResponse;
import org.cloudiator.messages.Byon.ByonNodeDeleteRequestMessage;
import org.cloudiator.messages.Byon.ByonNodeDeletedResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;

public class ByonServiceImpl  implements ByonService {

  private final MessageInterface messageInterface;

  @Inject
  ByonServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public void createByonPersistAllocAsync(
      ByonNodeAllocateRequestMessage byonNodeAllocateRequestMessage,
      ResponseCallback<ByonNodeAllocatedResponse> callback) {
    messageInterface
        .callAsync(byonNodeAllocateRequestMessage, ByonNodeAllocatedResponse.class, callback);
  }

  @Override
  public void createByonPersistDelAsync(ByonNodeDeleteRequestMessage byonNodeDeleteRequestMessage,
      ResponseCallback<ByonNodeDeletedResponse> callback) {
    messageInterface
        .callAsync(byonNodeDeleteRequestMessage, ByonNodeDeletedResponse.class, callback);
  }
}
