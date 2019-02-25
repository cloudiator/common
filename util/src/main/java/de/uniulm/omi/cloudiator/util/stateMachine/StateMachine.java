package de.uniulm.omi.cloudiator.util.stateMachine;

public interface StateMachine<O extends Stateful> {

  O apply(O object, State to, Object[] arguments);
}
