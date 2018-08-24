package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.Process.CreateLanceProcessRequest;
import org.cloudiator.messages.Process.CreateProcessRequest;
import org.cloudiator.messages.Process.CreateScheduleRequest;
import org.cloudiator.messages.Process.LanceProcessCreatedResponse;
import org.cloudiator.messages.Process.ProcessCreatedResponse;
import org.cloudiator.messages.Process.ScheduleCreatedResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

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
  public void subscribeSchedule(MessageCallback<CreateScheduleRequest> callback) {
    messageInterface
        .subscribe(CreateScheduleRequest.class, CreateScheduleRequest.parser(), callback);
  }

}
