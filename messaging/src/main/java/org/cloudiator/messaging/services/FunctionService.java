package org.cloudiator.messaging.services;

import org.cloudiator.messages.Function.*;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

public interface FunctionService {

  FunctionCreatedResponse createFunction(
      CreateFunctionRequestMessage createFunctionRequestMessage)
      throws ResponseException;

  void createFuntionAsync(CreateFunctionRequestMessage createFunctionRequestMessage,
      ResponseCallback<FunctionCreatedResponse> callback);

  void deleteFuntionAsync(DeleteFunctionRequestMessage deleteFunctionRequestMessage,
      ResponseCallback<FunctionDeletedResponse> callback);

}
