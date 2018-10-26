package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import org.cloudiator.messages.Process;
import org.cloudiator.messages.Process.*;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

import javax.inject.Named;

public class ProcessServiceImpl implements ProcessService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  public ProcessServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public ScheduleQueryResponse querySchedules(ScheduleQueryRequest scheduleQueryRequest)
      throws ResponseException {
    return this.messageInterface
        .call(scheduleQueryRequest, ScheduleQueryResponse.class, timeout);
  }

  @Override
  public ProcessQueryResponse queryProcesses(ProcessQueryRequest processQueryRequest)
      throws ResponseException {
    return this.messageInterface.call(processQueryRequest, ProcessQueryResponse.class, timeout);
  }

  @Override
  public void subscribeScheduleQueryRequest(MessageCallback<ScheduleQueryRequest> callback) {
    this.messageInterface
        .subscribe(ScheduleQueryRequest.class, ScheduleQueryRequest.parser(), callback);
  }

  @Override
  public void subscribeProcessQueryRequest(MessageCallback<ProcessQueryRequest> callback) {
    messageInterface.subscribe(ProcessQueryRequest.class, ProcessQueryRequest.parser(), callback);
  }

  @Override
  public ScheduleCreatedResponse createSchedule(CreateScheduleRequest createScheduleRequest)
      throws ResponseException {
    return messageInterface.call(createScheduleRequest, ScheduleCreatedResponse.class, timeout);
  }

  @Override
  public void createScheduleAsync(CreateScheduleRequest createScheduleRequest,
      ResponseCallback<ScheduleCreatedResponse> callback) {
    messageInterface.callAsync(createScheduleRequest, ScheduleCreatedResponse.class, callback);
  }

  @Override
  public ProcessCreatedResponse createProcess(CreateProcessRequest createProcessRequest)
      throws ResponseException {
    return messageInterface.call(createProcessRequest, ProcessCreatedResponse.class, timeout);
  }

  @Override
  public void createProcessAsync(CreateProcessRequest createProcessRequest,
      ResponseCallback<ProcessCreatedResponse> callback) {
    messageInterface.callAsync(createProcessRequest, ProcessCreatedResponse.class, callback);
  }

  @Override
  public void subscribeCreateProcessRequest(MessageCallback<CreateProcessRequest> callback) {
    messageInterface.subscribe(CreateProcessRequest.class, CreateProcessRequest.parser(), callback);
  }

  @Override
  public LanceProcessCreatedResponse createLanceProcess(
      CreateLanceProcessRequest createLanceProcessRequest) throws ResponseException {
    return messageInterface
        .call(createLanceProcessRequest, LanceProcessCreatedResponse.class, timeout);
  }

  @Override
  public void createLanceProcessAsync(CreateLanceProcessRequest createLanceProcessRequest,
      ResponseCallback<LanceProcessCreatedResponse> callback) {
    messageInterface
        .callAsync(createLanceProcessRequest, LanceProcessCreatedResponse.class, callback);
  }

  @Override
  public void subscribeCreateLanceProcessRequest(
      MessageCallback<CreateLanceProcessRequest> callback) {
    messageInterface
        .subscribe(CreateLanceProcessRequest.class, CreateLanceProcessRequest.parser(), callback);
  }

  @Override
  public FaasProcessCreatedResponse createFaasProcess(Process.CreateFaasProcessRequest createFaasProcessRequest) throws ResponseException {
    return messageInterface.call(createFaasProcessRequest, FaasProcessCreatedResponse.class, timeout);
  }

  @Override
  public void createFaasProcessAsync(Process.CreateFaasProcessRequest createFaasProcessRequest, ResponseCallback<Process.FaasProcessCreatedResponse> callback) {
    messageInterface.callAsync(createFaasProcessRequest, FaasProcessCreatedResponse.class, callback);
  }

  @Override
  public void subscribeCreateFaasProcessRequest(MessageCallback<Process.CreateFaasProcessRequest> callback) {
    messageInterface.subscribe(CreateFaasProcessRequest.class, CreateFaasProcessRequest.parser(), callback);
  }

  @Override
  public void subscribeSchedule(MessageCallback<CreateScheduleRequest> callback) {
    messageInterface
        .subscribe(CreateScheduleRequest.class, CreateScheduleRequest.parser(), callback);
  }

}
