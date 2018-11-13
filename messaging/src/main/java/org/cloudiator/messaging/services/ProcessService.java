package org.cloudiator.messaging.services;

import org.cloudiator.messages.Process.CreateLanceProcessRequest;
import org.cloudiator.messages.Process.CreateProcessRequest;
import org.cloudiator.messages.Process.CreateScheduleRequest;
import org.cloudiator.messages.Process.CreateSparkProcessRequest;
import org.cloudiator.messages.Process.DeleteProcessRequest;
import org.cloudiator.messages.Process.LanceProcessCreatedResponse;
import org.cloudiator.messages.Process.ProcessCreatedResponse;
import org.cloudiator.messages.Process.ProcessDeletedResponse;
import org.cloudiator.messages.Process.ProcessQueryRequest;
import org.cloudiator.messages.Process.ProcessQueryResponse;
import org.cloudiator.messages.Process.ScheduleCreatedResponse;
import org.cloudiator.messages.Process.ScheduleQueryRequest;
import org.cloudiator.messages.Process.ScheduleQueryResponse;
import org.cloudiator.messages.Process.SparkProcessCreatedResponse;
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

  ProcessDeletedResponse deleteProcess(DeleteProcessRequest deleteProcessRequest)
      throws ResponseException;

  void createProcessAsync(CreateProcessRequest createProcessRequest,
      ResponseCallback<ProcessCreatedResponse> callback);

  void deleteProcessAsync(DeleteProcessRequest deleteProcessRequest,
      ResponseCallback<ProcessDeletedResponse> callback);

  void subscribeCreateProcessRequest(MessageCallback<CreateProcessRequest> callback);

  void subscribeDeleteProcessRequest(MessageCallback<DeleteProcessRequest> callback);

  LanceProcessCreatedResponse createLanceProcess(
      CreateLanceProcessRequest createLanceProcessRequest)
      throws ResponseException;

  void createLanceProcessAsync(CreateLanceProcessRequest createLanceProcessRequest,
      ResponseCallback<LanceProcessCreatedResponse> callback);

  void subscribeCreateLanceProcessRequest(MessageCallback<CreateLanceProcessRequest> callback);

  SparkProcessCreatedResponse createSparkProcess(
      CreateSparkProcessRequest createSparkProcessRequest)
      throws ResponseException;

  void createSparkProcessAsync(CreateSparkProcessRequest createSparkProcessRequest,
      ResponseCallback<SparkProcessCreatedResponse> callback);

  void subscribeCreateSparkProcessRequest(MessageCallback<CreateSparkProcessRequest> callback);

  void subscribeSchedule(MessageCallback<CreateScheduleRequest> callback);
}
