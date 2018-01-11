package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.Installation.InstallationRequest;
import org.cloudiator.messages.Installation.InstallationResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;

/**
 * Created by Daniel Seybold on 12.09.2017.
 */
public class InstallationRequestServiceImpl implements InstallationRequestService {

  private final MessageInterface messageInterface;
  private long timeout = 0;

  @Inject
  public void setResponseTimeout(@Named("responseTimeout") long timeout) {
    this.timeout = timeout;
  }

  @Inject
  public InstallationRequestServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }


  @Override
  public void createInstallationRequestAsync(InstallationRequest installationRequest,
      ResponseCallback<InstallationResponse> callback) {

    messageInterface.callAsync(installationRequest, InstallationResponse.class, callback);

  }
}
