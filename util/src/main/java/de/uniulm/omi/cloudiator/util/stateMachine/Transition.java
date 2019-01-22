package de.uniulm.omi.cloudiator.util.stateMachine;

import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.util.function.ThrowingFunction;
import java.util.concurrent.ExecutionException;

public class Transition<O extends Stateful> {

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
  
  public O apply(O o, Object[] arguments) throws ExecutionException {
    try {
      return action.apply(o);
    } catch (Exception e) {
      throw new ExecutionException(e);
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("from", from).add("to", to).toString();
  }
}
