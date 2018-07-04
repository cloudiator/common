package org.cloudiator.messaging.services;

import org.cloudiator.messages.Process.CreateScheduleRequest;
import org.cloudiator.messages.Process.ScheduleCreatedResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.ResponseException;

public interface ProcessService {

  ScheduleCreatedResponse createSchedule(CreateScheduleRequest createScheduleRequest)
      throws ResponseException;


  void subscribeSchedule(MessageCallback<CreateScheduleRequest> callback);
}
