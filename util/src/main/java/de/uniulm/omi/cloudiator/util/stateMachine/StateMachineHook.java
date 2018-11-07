package de.uniulm.omi.cloudiator.util.stateMachine;

public interface StateMachineHook<O> {

  void pre(O object, State to);

  void post(State from, O object);

}
