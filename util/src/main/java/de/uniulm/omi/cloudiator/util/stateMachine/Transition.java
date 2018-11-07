package de.uniulm.omi.cloudiator.util.stateMachine;

import de.uniulm.omi.cloudiator.util.function.ThrowingFunction;
import java.util.concurrent.ExecutionException;

public class Transition<O extends Stateful> implements ThrowingFunction<O, O> {

  private final State from;
  private final State to;
  private final ThrowingFunction<O, O> action;

  protected Transition(State from, State to,
      ThrowingFunction<O, O> action) {
    this.from = from;
    this.to = to;
    this.action = action;
  }

  public State from() {
    return from;
  }

  public State to() {
    return to;
  }

  @Override
  public O apply(O o) throws ExecutionException {
    try {
      return action.apply(o);
    } catch (Exception e) {
      throw new ExecutionException(e);
    }
  }
}
