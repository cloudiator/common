package de.uniulm.omi.cloudiator.util.stateMachine;

public class Transitions {

  public static <O extends Stateful> TransitionBuilder<O> transitionBuilder() {
    return new TransitionBuilder<>();
  }

  public static <O extends Stateful> ErrorTransitionBuilder<O> errorTransitionBuilder() {
    return new ErrorTransitionBuilder<>();
  }

}
