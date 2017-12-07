package org.cloudiator.messaging.services;

import org.cloudiator.messages.entities.Matchmaking.MatchmakingRequest;
import org.cloudiator.messages.entities.Matchmaking.MatchmakingResponse;
import org.cloudiator.messages.entities.Matchmaking.NodeCandidateRequestMessage;
import org.cloudiator.messages.entities.Matchmaking.NodeCandidateRequestResponse;
import org.cloudiator.messages.entities.Matchmaking.OclSolutionRequest;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

public interface MatchmakingService {

  NodeCandidateRequestResponse requestNodes(NodeCandidateRequestMessage nodeCandidateRequestMessage)
      throws ResponseException;

  void requestNodesAsync(NodeCandidateRequestMessage nodeCandidateRequestMessage,
      ResponseCallback<NodeCandidateRequestResponse> callback);

  MatchmakingResponse solveOCLProblem(OclSolutionRequest solutionRequest) throws ResponseException;

  void solveOCLProblemAsync(OclSolutionRequest solutionRequest,
      ResponseCallback<MatchmakingResponse> callback);

  MatchmakingResponse requestMatch(MatchmakingRequest matchmakingRequest) throws ResponseException;

  void requestMatchAsync(MatchmakingRequest matchmakingRequest,
      ResponseCallback<MatchmakingResponse> response);

}
