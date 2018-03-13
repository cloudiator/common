/*
 * Copyright 2017 University of Ulm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.Job.CreateJobRequest;
import org.cloudiator.messages.Job.DeleteJobRequest;
import org.cloudiator.messages.Job.JobCreatedResponse;
import org.cloudiator.messages.Job.JobDeletedResponse;
import org.cloudiator.messages.Job.JobQueryRequest;
import org.cloudiator.messages.Job.JobQueryResponse;
import org.cloudiator.messages.Job.JobUpdatedResponse;
import org.cloudiator.messages.Job.UpdateJobRequest;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 23.06.17.
 */
public class JobServiceImpl implements JobService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  JobServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public void subscribeToJobQueryRequest(MessageCallback<JobQueryRequest> callback) {
    messageInterface.subscribe(JobQueryRequest.class, JobQueryRequest.parser(), callback);
  }

  @Override
  public void subscribeToCreateJobRequest(MessageCallback<CreateJobRequest> callback) {
    messageInterface.subscribe(CreateJobRequest.class, CreateJobRequest.parser(), callback);
  }

  @Override
  public JobQueryResponse getJobs(JobQueryRequest jobQueryRequest) throws ResponseException {
    return messageInterface.call(jobQueryRequest, JobQueryResponse.class);
  }

  @Override
  public JobCreatedResponse createJob(CreateJobRequest createJobRequest) throws ResponseException {
    return messageInterface.call(createJobRequest, JobCreatedResponse.class, timeout);
  }

  @Override
  public JobUpdatedResponse updateJob(UpdateJobRequest updateJobRequest) throws ResponseException {
    return messageInterface.call(updateJobRequest, JobUpdatedResponse.class, timeout);
  }

  @Override
  public JobDeletedResponse deleteJob(DeleteJobRequest deleteJobRequest) throws ResponseException {
    return messageInterface.call(deleteJobRequest, JobDeletedResponse.class, timeout);
  }
}
