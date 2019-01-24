package de.uniulm.omi.cloudiator.util.stateMachine;

public interface ErrorAwareStateMachine<O extends Stateful> extends StateMachine<O> {

  O fail(O object, Object[] arguments, Throwable t);
}
