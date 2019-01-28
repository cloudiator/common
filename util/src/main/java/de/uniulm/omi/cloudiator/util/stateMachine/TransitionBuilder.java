package de.uniulm.omi.cloudiator.util.stateMachine;

import de.uniulm.omi.cloudiator.util.stateMachine.Transition.TransitionAction;

public class TransitionBuilder<O extends Stateful> {

  private State from;
  private State to;
  private TransitionAction<O> action;

  TransitionBuilder() {
  }

  public TransitionBuilder<O> from(State from) {
    this.from = from;
    return this;
  }

  public TransitionBuilder<O> to(State to) {
    this.to = to;
    return this;
  }

  public TransitionBuilder<O> action(TransitionAction<O> action) {
    this.action = action;
    return this;
  }

  public Transition<O> build() {
    return new Transition<>(from, to, action);
  }

}
