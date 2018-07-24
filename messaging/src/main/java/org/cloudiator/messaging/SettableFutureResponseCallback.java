package org.cloudiator.messaging;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.cloudiator.messages.General.Error;

public class SettableFutureResponseCallback<R, F> implements ResponseCallback<R>,
    ListenableFuture<F> {

  private final SettableFuture<F> future;
  private final ResponseCallback<R> responseCallback;

  public static <F> SettableFutureResponseCallback<F, F> create() {
    return new SettableFutureResponseCallback<>(Function.identity());
  }

  public static <R, F> SettableFutureResponseCallback<R, F> create(Function<R, F> converter) {
    return new SettableFutureResponseCallback<>(converter);
  }

  private SettableFutureResponseCallback(Function<R, F> converter) {
    future = SettableFuture.create();
    responseCallback = new ResponseCallback<R>() {
      @Override
      public void accept(@Nullable R content, @Nullable Error error) {
        if (content != null) {
          future.set(converter.apply(content));
        } else if (error != null) {
          future.setException(new ResponseException(error.getCode(), error.getMessage()));
        } else {
          throw new IllegalStateException("Neither content or error are set");
        }
      }
    };
  }
  
  @Override
  public boolean cancel(boolean b) {
    return future.cancel(b);
  }

  @Override
  public boolean isCancelled() {
    return future.isCancelled();
  }

  @Override
  public boolean isDone() {
    return future.isDone();
  }

  @Override
  public F get() throws InterruptedException, ExecutionException {
    return future.get();
  }

  @Override
  public F get(long l, TimeUnit timeUnit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return future.get(l, timeUnit);
  }


  @Override
  public void addListener(Runnable listener, Executor executor) {
    future.addListener(listener, executor);
  }

  @Override
  public void accept(@Nullable R content, @Nullable Error error) {
    responseCallback.accept(content, error);
  }
}
