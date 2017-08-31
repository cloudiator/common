package org.cloudiator.messaging.services;

import org.cloudiator.messages.entities.Solution.OclSolutionRequest;
import org.cloudiator.messages.entities.Solution.OclSolutionResponse;
import org.cloudiator.messages.entities.SolutionEntities.OclProblem;
import org.cloudiator.messages.entities.SolutionEntities.OclSolution;
import org.cloudiator.messaging.ResponseException;

public interface SolutionService {

  OclSolutionResponse solveOCLProblem(OclSolutionRequest solutionRequest) throws ResponseException;

}
