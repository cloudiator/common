package de.uniulm.omi.cloudiator.util.stateMachine;

import java.util.concurrent.ExecutionException;

public interface StateMachine<O extends Stateful> {

  O apply(O object, State to) throws ExecutionException;
}
