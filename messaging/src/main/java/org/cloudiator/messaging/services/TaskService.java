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

import org.cloudiator.messages.Task.CreateTaskRequest;
import org.cloudiator.messages.Task.DeleteTaskRequest;
import org.cloudiator.messages.Task.TaskCreatedResponse;
import org.cloudiator.messages.Task.TaskDeletedResponse;
import org.cloudiator.messages.Task.TaskQueryRequest;
import org.cloudiator.messages.Task.TaskQueryResponse;
import org.cloudiator.messages.Task.TaskUpdatedResponse;
import org.cloudiator.messages.Task.UpdateTaskRequest;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 23.06.17.
 */
public interface TaskService {

  TaskQueryResponse getTasks(TaskQueryRequest taskQueryRequest)
      throws ResponseException;

  TaskCreatedResponse createTask(CreateTaskRequest createTaskRequest)
      throws ResponseException;

  TaskUpdatedResponse updateTask(UpdateTaskRequest updateTaskRequest)
      throws ResponseException;

  TaskDeletedResponse deleteTask(DeleteTaskRequest deleteTaskRequest)
      throws ResponseException;

}
