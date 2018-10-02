package org.cloudiator.messaging.services;

import org.cloudiator.messages.entities.SecureStore.SecureStoreDeleteRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreDeleteResponse;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreResponse;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRetrieveRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRetrieveResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.ResponseException;

public interface SecureStoreService {

  SecureStoreResponse storeSecurely(SecureStoreRequest secureStoreRequest) throws ResponseException;

  SecureStoreRetrieveResponse retrieveSecret(SecureStoreRetrieveRequest secureRetrieveRequest)
      throws ResponseException;

  SecureStoreDeleteResponse deleteSecret(SecureStoreDeleteRequest secureStoreDeleteRequest)
      throws ResponseException;

  void subscribeStoreRequest(MessageCallback<SecureStoreRequest> callback);

  void subscribeRetrieveRequest(MessageCallback<SecureStoreRetrieveRequest> callback);

  void subscribeDeleteRequest(MessageCallback<SecureStoreDeleteRequest> callback);

}
