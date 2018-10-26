package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import org.cloudiator.messages.Function.CreateFunctionRequestMessage;
import org.cloudiator.messages.Function.DeleteFunctionRequestMessage;
import org.cloudiator.messages.Function.FunctionCreatedResponse;
import org.cloudiator.messages.Function.FunctionDeletedResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

import javax.inject.Named;

public class FunctionServiceImpl implements FunctionService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  FunctionServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public FunctionCreatedResponse createFunction(
      CreateFunctionRequestMessage createFunctionRequestMessage) throws ResponseException {
    return messageInterface.call(createFunctionRequestMessage, FunctionCreatedResponse.class, timeout);
  }

  @Override
  public void createFuntionAsync(
      CreateFunctionRequestMessage createFunctionRequestMessage,
      ResponseCallback<FunctionCreatedResponse> callback) {
    messageInterface
        .callAsync(createFunctionRequestMessage, FunctionCreatedResponse.class, callback);
  }

  @Override
  public void deleteFuntionAsync(
      DeleteFunctionRequestMessage deleteFunctionRequestMessage,
      ResponseCallback<FunctionDeletedResponse> callback) {
    messageInterface
        .callAsync(deleteFunctionRequestMessage, FunctionDeletedResponse.class, callback);

  }
}
