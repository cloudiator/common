package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.entities.Solution.OclSolutionRequest;
import org.cloudiator.messages.entities.Solution.OclSolutionResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

public class SolutionServiceImpl implements SolutionService {

  private final MessageInterface messageInterface;
  private long timeout = 0;

  @Inject
  public SolutionServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Inject
  public void setResponseTimeout(@Named("responseTimeout") long timeout) {
    this.timeout = timeout;
  }

  @Override
  public OclSolutionResponse solveOCLProblem(OclSolutionRequest oclSolutionRequest)
      throws ResponseException {
    return messageInterface.call(oclSolutionRequest, OclSolutionResponse.class, timeout);
  }

  @Override
  public void solveOCLProblemAsync(OclSolutionRequest solutionRequest,
      ResponseCallback<OclSolutionResponse> callback) {
    messageInterface.callAsync(solutionRequest, OclSolutionResponse.class, callback);
  }
}
