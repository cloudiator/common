package de.uniulm.omi.cloudiator.util.stateMachine;

import de.uniulm.omi.cloudiator.util.stateMachine.ErrorTransition.ErrorTransitionAction;

public class ErrorTransitionBuilder<O extends Stateful> {

  private State errorState;
  private ErrorTransitionAction<O> action;

  ErrorTransitionBuilder() {
  }

  public ErrorTransitionBuilder<O> errorState(State errorState) {
    this.errorState = errorState;
    return this;
  }

  public ErrorTransitionBuilder<O> action(ErrorTransitionAction<O> action) {
    this.action = action;
    return this;
  }

  public ErrorTransition<O> build() {
    return new ErrorTransition<O>(errorState, action);
  }

}
