package de.uniulm.omi.cloudiator.util.stateMachine;

import javax.annotation.Nullable;

public class ErrorTransition<O extends Stateful> {

  public State errorState() {
    return errorState;
  }

  public interface ErrorTransitionAction<O> {

    O apply(O o, Object[] arguments, @Nullable Throwable t);
  }

  private final State errorState;
  private final ErrorTransitionAction<O> errorTransitionAction;


  ErrorTransition(State errorState,
      ErrorTransitionAction<O> errorTransitionAction) {
    this.errorState = errorState;
    this.errorTransitionAction = errorTransitionAction;
  }

  O apply(O object, Object[] arguments, @Nullable Throwable t) {
    return errorTransitionAction.apply(object, arguments, t);
  }
}
