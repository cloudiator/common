package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.entities.SecureStore.SecureRetrieveRequest;
import org.cloudiator.messages.entities.SecureStore.SecureRetrieveResponse;
import org.cloudiator.messages.entities.SecureStore.SecureStoreRequest;
import org.cloudiator.messages.entities.SecureStore.SecureStoreResponse;
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
  public SecureRetrieveResponse retrieveSecret(SecureRetrieveRequest secureRetrieveRequest)
      throws ResponseException {
    return this.messageInterface.call(secureRetrieveRequest, SecureRetrieveResponse.class, timeout);
  }

  @Override
  public void subscribeStoreRequest(MessageCallback<SecureStoreRequest> callback) {
    this.messageInterface
        .subscribe(SecureStoreRequest.class, SecureStoreRequest.parser(), callback);
  }

  @Override
  public void subscribeRetrieveRequest(MessageCallback<SecureRetrieveRequest> callback) {
    this.messageInterface
        .subscribe(SecureRetrieveRequest.class, SecureRetrieveRequest.parser(), callback);
  }
}
