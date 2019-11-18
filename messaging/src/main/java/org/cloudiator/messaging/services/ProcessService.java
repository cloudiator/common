package org.cloudiator.messaging.services;

import javax.annotation.Nullable;
import org.cloudiator.messages.Process.CreateFaasProcessRequest;
import org.cloudiator.messages.Process.CreateHdfsClusterRequest;
import org.cloudiator.messages.Process.CreateHdfsProcessRequest;
import org.cloudiator.messages.Process.CreateLanceProcessRequest;
import org.cloudiator.messages.Process.CreateProcessRequest;
import org.cloudiator.messages.Process.CreateScheduleRequest;
import org.cloudiator.messages.Process.CreateSparkClusterRequest;
import org.cloudiator.messages.Process.CreateSparkProcessRequest;
import org.cloudiator.messages.Process.DeleteLanceProcessRequest;
import org.cloudiator.messages.Process.DeleteProcessRequest;
import org.cloudiator.messages.Process.DeleteScheduleRequest;
import org.cloudiator.messages.Process.FaasProcessCreatedResponse;
import org.cloudiator.messages.Process.HdfsClusterCreatedResponse;
import org.cloudiator.messages.Process.HdfsProcessCreatedResponse;
import org.cloudiator.messages.Process.LanceProcessCreatedResponse;
import org.cloudiator.messages.Process.LanceProcessDeletedResponse;
import org.cloudiator.messages.Process.LanceUpdateRequest;
import org.cloudiator.messages.Process.LanceUpdateResponse;
import org.cloudiator.messages.Process.ProcessCreatedResponse;
import org.cloudiator.messages.Process.ProcessDeletedResponse;
import org.cloudiator.messages.Process.ProcessEvent;
import org.cloudiator.messages.Process.ProcessQueryRequest;
import org.cloudiator.messages.Process.ProcessQueryResponse;
import org.cloudiator.messages.Process.ProcessStatusQuery;
import org.cloudiator.messages.Process.ProcessStatusResponse;
import org.cloudiator.messages.Process.ScaleRequest;
import org.cloudiator.messages.Process.ScaleResponse;
import org.cloudiator.messages.Process.ScheduleCreatedResponse;
import org.cloudiator.messages.Process.ScheduleDeleteResponse;
import org.cloudiator.messages.Process.ScheduleEvent;
import org.cloudiator.messages.Process.ScheduleGraphRequest;
import org.cloudiator.messages.Process.ScheduleGraphResponse;
import org.cloudiator.messages.Process.ScheduleQueryRequest;
import org.cloudiator.messages.Process.ScheduleQueryResponse;
import org.cloudiator.messages.Process.SparkClusterCreatedResponse;
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

  void subscribeScheduleDeleteRequest(MessageCallback<DeleteScheduleRequest> callback);

  void subscribeProcessQueryRequest(MessageCallback<ProcessQueryRequest> callback);

  ScheduleCreatedResponse createSchedule(CreateScheduleRequest createScheduleRequest)
      throws ResponseException;

  void createScheduleAsync(CreateScheduleRequest createScheduleRequest,
      ResponseCallback<ScheduleCreatedResponse> callback);

  void deleteScheduleAsync(DeleteScheduleRequest deleteScheduleRequest,
      ResponseCallback<ScheduleDeleteResponse> callback);

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

  void deleteLanceProcessAsync(DeleteLanceProcessRequest deleteLanceProcessRequest,
      ResponseCallback<LanceProcessDeletedResponse> callback);

  void subscribeDeleteLanceProcessRequest(MessageCallback<DeleteLanceProcessRequest> callback);

  // Spark

  SparkProcessCreatedResponse createSparkProcess(
      CreateSparkProcessRequest createSparkProcessRequest)
      throws ResponseException;

  void createSparkProcessAsync(CreateSparkProcessRequest createSparkProcessRequest,
      ResponseCallback<SparkProcessCreatedResponse> callback);

  void subscribeCreateSparkProcessRequest(MessageCallback<CreateSparkProcessRequest> callback);


  SparkClusterCreatedResponse createSparkCluster(CreateSparkClusterRequest createSparkClusterRequest)
      throws ResponseException;

  void createSparkClusterAsync(CreateSparkClusterRequest createSparkClusterRequest,
      ResponseCallback<SparkClusterCreatedResponse> callback);

  void subscribeCreateSparkClusterRequest(MessageCallback<CreateSparkClusterRequest> callback);

  // Hdfs

  HdfsProcessCreatedResponse createHdfsProcess(
      CreateHdfsProcessRequest createHdfsProcessRequest)
      throws ResponseException;

  void createHdfsProcessAsync(CreateHdfsProcessRequest createHdfsProcessRequest,
      ResponseCallback<HdfsProcessCreatedResponse> callback);

  void subscribeCreateHdfsProcessRequest(MessageCallback<CreateHdfsProcessRequest> callback);


  HdfsClusterCreatedResponse createHdfsCluster(CreateHdfsClusterRequest createHdfsClusterRequest)
      throws ResponseException;

  void createHdfsClusterAsync(CreateHdfsClusterRequest createHdfsClusterRequest,
      ResponseCallback<HdfsClusterCreatedResponse> callback);

  void subscribeCreateHdfsClusterRequest(MessageCallback<CreateHdfsClusterRequest> callback);

  FaasProcessCreatedResponse createFaasProcess(CreateFaasProcessRequest createFaasProcessRequest)
      throws ResponseException;

  void createFaasProcessAsync(CreateFaasProcessRequest createFaasProcessRequest,
      ResponseCallback<FaasProcessCreatedResponse> callback);

  void subscribeCreateFaasProcessRequest(MessageCallback<CreateFaasProcessRequest> callback);

  void subscribeSchedule(MessageCallback<CreateScheduleRequest> callback);

  void announceProcessEvent(ProcessEvent processEvent);

  void subscribeProcessEvent(MessageCallback<ProcessEvent> callback);

  ScheduleGraphResponse graph(ScheduleGraphRequest scheduleGraphRequest) throws ResponseException;

  ProcessStatusResponse queryProcessStatus(ProcessStatusQuery processStatusQuery,
      @Nullable Long timeout)
      throws ResponseException;

  void announceScheduleEvent(ScheduleEvent scheduleEvent);

  ScaleResponse createScale(ScaleRequest scaleRequest)
      throws ResponseException;

  void createScaleRequestAsync(ScaleRequest scaleRequest,
      ResponseCallback<ScaleResponse> callback);

  void subscribeScaleRequest(MessageCallback<ScaleRequest> callback);

  void updateLanceEnvironmentAsync(LanceUpdateRequest lanceUpdateRequest,
      ResponseCallback<LanceUpdateResponse> callback);
}
