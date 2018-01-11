package org.cloudiator.messaging.services;

import org.cloudiator.messages.Installation.InstallationRequest;
import org.cloudiator.messages.Installation.InstallationResponse;
import org.cloudiator.messaging.ResponseCallback;

/**
 * Created by Daniel Seybold on 12.09.2017.
 */
public interface InstallationRequestService {


  void createInstallationRequestAsync(InstallationRequest installationRequest, ResponseCallback<InstallationResponse> callback);

}
