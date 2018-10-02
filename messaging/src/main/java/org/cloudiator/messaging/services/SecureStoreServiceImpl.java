package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.entities.SecureStore.SecureStoreDeleteRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreDeleteResponse;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreResponse;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRetrieveRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRetrieveResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

public class SecureStoreServiceImpl implements SecureStoreService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  public SecureStoreServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public SecureStoreResponse storeSecurely(SecureStoreRequest secureStoreRequest)
      throws ResponseException {
    return this.messageInterface.call(secureStoreRequest, SecureStoreResponse.class, timeout);
  }

  @Override
  public SecureStoreRetrieveResponse retrieveSecret(
      SecureStoreRetrieveRequest secureRetrieveRequest)
      throws ResponseException {
    return this.messageInterface
        .call(secureRetrieveRequest, SecureStoreRetrieveResponse.class, timeout);
  }

  @Override
  public SecureStoreDeleteResponse deleteSecret(SecureStoreDeleteRequest secureStoreDeleteRequest)
      throws ResponseException {
    return this.messageInterface
        .call(secureStoreDeleteRequest, SecureStoreDeleteResponse.class, timeout);
  }

  @Override
  public void subscribeStoreRequest(MessageCallback<SecureStoreRequest> callback) {
    this.messageInterface
        .subscribe(SecureStoreRequest.class, SecureStoreRequest.parser(), callback);
  }

  @Override
  public void subscribeRetrieveRequest(MessageCallback<SecureStoreRetrieveRequest> callback) {
    this.messageInterface
        .subscribe(SecureStoreRetrieveRequest.class, SecureStoreRetrieveRequest.parser(), callback);
  }

  @Override
  public void subscribeDeleteRequest(MessageCallback<SecureStoreDeleteRequest> callback) {
    this.messageInterface
        .subscribe(SecureStoreDeleteRequest.class, SecureStoreDeleteRequest.parser(), callback);
  }
}
