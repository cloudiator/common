package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import com.google.inject.name.Named;
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
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

public class ByonServiceImpl  implements ByonService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

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

  @Override
  public void addByonNodeAsync(AddByonNodeRequest addByonNodeRequest,
      ResponseCallback<ByonNodeAddedResponse> callback) {
    messageInterface
        .callAsync(addByonNodeRequest, ByonNodeAddedResponse.class, callback);
  }

  @Override
  public void removeByonNodeAsync(RemoveByonNodeRequest removeByonNodeRequest,
      ResponseCallback<ByonNodeRemovedResponse> callback) {
    messageInterface
        .callAsync(removeByonNodeRequest, ByonNodeRemovedResponse.class, callback);
  }

  @Override
  public ByonNodeQueryResponse findByonNodes(ByonNodeQueryRequest byonQueryRequest)
      throws ResponseException {
    return messageInterface.call(byonQueryRequest, ByonNodeQueryResponse.class, timeout);
  }

  @Override
  public void subscribeByonNodeEvents(MessageCallback<ByonNodeEvent> callback) {
    messageInterface.subscribe(ByonNodeEvent.class, ByonNodeEvent.parser(), callback);
  }
}
