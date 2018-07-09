package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.entities.Encryption.DecryptionRequest;
import org.cloudiator.messages.entities.Encryption.DecryptionResponse;
import org.cloudiator.messages.entities.Encryption.EncryptionRequest;
import org.cloudiator.messages.entities.Encryption.EncryptionResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

public class EncryptionServiceImpl implements EncryptionService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  public EncryptionServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public EncryptionResponse encrypt(EncryptionRequest encryptionRequest) throws ResponseException {
    return messageInterface.call(encryptionRequest, EncryptionResponse.class, timeout);
  }

  @Override
  public DecryptionResponse decrypt(DecryptionRequest decryptionRequest) throws ResponseException {
    return messageInterface.call(decryptionRequest, DecryptionResponse.class, timeout);
  }

  @Override
  public void subscribeEncryption(MessageCallback<EncryptionRequest> callback) {
    messageInterface.subscribe(EncryptionRequest.class, EncryptionRequest.parser(), callback);
  }

  @Override
  public void subscribeDecryption(MessageCallback<DecryptionRequest> callback) {
    messageInterface.subscribe(DecryptionRequest.class, DecryptionRequest.parser(), callback);
  }
}
