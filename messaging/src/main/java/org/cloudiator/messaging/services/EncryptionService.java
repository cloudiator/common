package org.cloudiator.messaging.services;

import org.cloudiator.messages.entities.Encryption.DecryptionRequest;
import org.cloudiator.messages.entities.Encryption.DecryptionResponse;
import org.cloudiator.messages.entities.Encryption.EncryptionRequest;
import org.cloudiator.messages.entities.Encryption.EncryptionResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.ResponseException;

public interface EncryptionService {

  EncryptionResponse encrypt(EncryptionRequest encryptionRequest) throws ResponseException;

  DecryptionResponse decrypt(DecryptionRequest decryptionRequest) throws ResponseException;

  void subscribeEncryption(MessageCallback<EncryptionRequest> callback);

  void subscribeDecryption(MessageCallback<DecryptionRequest> callback);
}
