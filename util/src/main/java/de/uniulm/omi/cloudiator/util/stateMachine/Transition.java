package de.uniulm.omi.cloudiator.util.stateMachine;

import com.google.common.base.MoreObjects;
import java.util.concurrent.ExecutionException;

public class Transition<O extends Stateful> {

  public interface TransitionAction<O> {

    O apply(O o, Object[] arguments) throws ExecutionException;

  }

  private final State from;
  private final State to;
  private final TransitionAction<O> action;

  Transition(State from, State to,
      TransitionAction<O> action) {
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

  O apply(O o, Object[] arguments) throws ExecutionException {
    try {
      return action.apply(o, arguments);
    } catch (Exception e) {
      throw new ExecutionException(e);
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("from", from).add("to", to).toString();
  }
}
