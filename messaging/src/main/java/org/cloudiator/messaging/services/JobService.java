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


import org.cloudiator.messages.Job.CreateJobRequest;
import org.cloudiator.messages.Job.DeleteJobRequest;
import org.cloudiator.messages.Job.JobCreatedResponse;
import org.cloudiator.messages.Job.JobDeletedResponse;
import org.cloudiator.messages.Job.JobQueryRequest;
import org.cloudiator.messages.Job.JobQueryResponse;
import org.cloudiator.messages.Job.JobUpdatedResponse;
import org.cloudiator.messages.Job.UpdateJobRequest;
import org.cloudiator.messages.Job.YAMLRequest;
import org.cloudiator.messages.Job.YAMLResponse;
import org.cloudiator.messaging.MessageCallback;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 23.06.17.
 */
public interface JobService {

  void subscribeToJobQueryRequest(MessageCallback<JobQueryRequest> callback);

  void subscribeToCreateJobRequest(MessageCallback<CreateJobRequest> callback);

  JobQueryResponse getJobs(JobQueryRequest jobQueryRequest)
      throws ResponseException;

  JobCreatedResponse createJob(CreateJobRequest createJobRequest)
      throws ResponseException;

  JobUpdatedResponse updateJob(UpdateJobRequest updateJobRequest)
      throws ResponseException;

  JobDeletedResponse deleteJob(DeleteJobRequest deleteJobRequest)
      throws ResponseException;

  void yamlAsync(YAMLRequest yamlRequest, ResponseCallback<YAMLResponse> callback);


}
