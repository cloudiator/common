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

package de.uniulm.omi.cloudiator.util.execution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An extension of the ScheduledThreadPoolExecutor, that logs any errors occurring during the
 * execution of tasks, instead of silently ignoring them.
 */
public class LoggingThreadPoolExecutor extends ThreadPoolExecutor {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LoggingThreadPoolExecutor.class);

  public LoggingThreadPoolExecutor(int i, int i1, long l, TimeUnit timeUnit,
      BlockingQueue<Runnable> blockingQueue) {
    super(i, i1, l, timeUnit, blockingQueue);
  }

  public LoggingThreadPoolExecutor(int i, int i1, long l, TimeUnit timeUnit,
      BlockingQueue<Runnable> blockingQueue,
      ThreadFactory threadFactory) {
    super(i, i1, l, timeUnit, blockingQueue, threadFactory);
  }

  public LoggingThreadPoolExecutor(int i, int i1, long l, TimeUnit timeUnit,
      BlockingQueue<Runnable> blockingQueue,
      RejectedExecutionHandler rejectedExecutionHandler) {
    super(i, i1, l, timeUnit, blockingQueue, rejectedExecutionHandler);
  }

  public LoggingThreadPoolExecutor(int i, int i1, long l, TimeUnit timeUnit,
      BlockingQueue<Runnable> blockingQueue,
      ThreadFactory threadFactory,
      RejectedExecutionHandler rejectedExecutionHandler) {
    super(i, i1, l, timeUnit, blockingQueue, threadFactory, rejectedExecutionHandler);
  }

  @Override
  protected void afterExecute(final Runnable r, final Throwable t) {
    super.afterExecute(r, t);
    Throwable tToLog = t;
    if (tToLog == null && r instanceof Future<?>) {
      try {
        if (((Future) r).isDone() && !((Future) r).isCancelled()) {
          ((Future) r).get();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } catch (ExecutionException e) {
        tToLog = e.getCause();
      }
    }
    if (tToLog != null) {
      LOGGER.error("Uncaught exception occurred during the execution of task " + r + ".",
          tToLog);
    }
  }
}
