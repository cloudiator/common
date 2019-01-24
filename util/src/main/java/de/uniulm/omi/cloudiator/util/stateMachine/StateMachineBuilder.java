package de.uniulm.omi.cloudiator.util.stateMachine;

import java.util.HashSet;

public class StateMachineBuilder<O extends Stateful> {

  private HashSet<Transition<O>> transitions;
  private HashSet<StateMachineHook<O>> hooks;
  private ErrorTransition<O> errorTransition;

  public static <O extends Stateful> StateMachineBuilder builder() {
    return new StateMachineBuilder<O>();
  }

  private StateMachineBuilder() {
    transitions = new HashSet<>();
    hooks = new HashSet<>();
  }

  public StateMachineBuilder<O> addTransition(Transition<O> transition) {
    transitions.add(transition);
    return this;
  }

  public StateMachineBuilder<O> addHook(StateMachineHook<O> hook) {
    hooks.add(hook);
    return this;
  }

  public StateMachineBuilder<O> errorTransition(ErrorTransition<O> errorTransition) {
    this.errorTransition = errorTransition;
    return this;
  }

  public ErrorAwareStateMachine<O> build() {
    return new StateMachineImpl<>(transitions, hooks, errorTransition);
  }


}
