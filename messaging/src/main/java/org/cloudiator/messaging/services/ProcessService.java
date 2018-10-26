package org.cloudiator.messaging.services;

import org.cloudiator.messages.Process;
import org.cloudiator.messages.Process.CreateLanceProcessRequest;
import org.cloudiator.messages.Process.CreateFaasProcessRequest;
import org.cloudiator.messages.Process.FaasProcessCreatedResponse;
import org.cloudiator.messages.Process.CreateProcessRequest;
import org.cloudiator.messages.Process.CreateScheduleRequest;
import org.cloudiator.messages.Process.LanceProcessCreatedResponse;
import org.cloudiator.messages.Process.ProcessCreatedResponse;
import org.cloudiator.messages.Process.ProcessQueryRequest;
import org.cloudiator.messages.Process.ProcessQueryResponse;
import org.cloudiator.messages.Process.ScheduleCreatedResponse;
import org.cloudiator.messages.Process.ScheduleQueryRequest;
import org.cloudiator.messages.Process.ScheduleQueryResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

public interface ProcessService {

  ScheduleQueryResponse querySchedules(ScheduleQueryRequest scheduleQueryRequest)
      throws ResponseException;

  ProcessQueryResponse queryProcesses(ProcessQueryRequest processQueryRequest)
      throws ResponseException;

  void subscribeScheduleQueryRequest(MessageCallback<ScheduleQueryRequest> callback);

  void subscribeProcessQueryRequest(MessageCallback<ProcessQueryRequest> callback);

  ScheduleCreatedResponse createSchedule(CreateScheduleRequest createScheduleRequest)
      throws ResponseException;

  void createScheduleAsync(CreateScheduleRequest createScheduleRequest,
      ResponseCallback<ScheduleCreatedResponse> callback);

  ProcessCreatedResponse createProcess(CreateProcessRequest createProcessRequest)
      throws ResponseException;

  void createProcessAsync(CreateProcessRequest createProcessRequest,
      ResponseCallback<ProcessCreatedResponse> callback);

  void subscribeCreateProcessRequest(MessageCallback<CreateProcessRequest> callback);

  LanceProcessCreatedResponse createLanceProcess(
      CreateLanceProcessRequest createLanceProcessRequest)
      throws ResponseException;

  void createLanceProcessAsync(CreateLanceProcessRequest createLanceProcessRequest,
      ResponseCallback<LanceProcessCreatedResponse> callback);

  void subscribeCreateLanceProcessRequest(MessageCallback<CreateLanceProcessRequest> callback);

  FaasProcessCreatedResponse createFaasProcess(CreateFaasProcessRequest createFaasProcessRequest)
      throws ResponseException;

  void createFaasProcessAsync(CreateFaasProcessRequest createFaasProcessRequest,
      ResponseCallback<FaasProcessCreatedResponse> callback);

  void subscribeCreateFaasProcessRequest(MessageCallback<CreateFaasProcessRequest> callback);

  void subscribeSchedule(MessageCallback<CreateScheduleRequest> callback);
}
