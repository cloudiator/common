package de.uniulm.omi.cloudiator.util.function;

import java.util.concurrent.ExecutionException;

@FunctionalInterface
public interface ThrowingFunction<T, R> {

  R apply(T t) throws ExecutionException;
}
