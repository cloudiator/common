package org.cloudiator.messaging.services;

import org.cloudiator.messages.entities.SecureStore.SecureRetrieveRequest;
import org.cloudiator.messages.entities.SecureStore.SecureRetrieveResponse;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.ResponseException;

public interface SecureStoreService {

  SecureStoreResponse storeSecurely(SecureStoreRequest secureStoreRequest) throws ResponseException;

  SecureRetrieveResponse retrieveSecret(SecureRetrieveRequest secureRetrieveRequest)
      throws ResponseException;

  void subscribeStoreRequest(MessageCallback<SecureStoreRequest> callback);

  void subscribeRetrieveRequest(MessageCallback<SecureRetrieveRequest> callback);

}
