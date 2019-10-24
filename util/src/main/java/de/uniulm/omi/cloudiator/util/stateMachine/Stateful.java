package de.uniulm.omi.cloudiator.util.stateMachine;

import de.uniulm.omi.cloudiator.domain.Identifiable;

public interface Stateful<S extends State> extends Identifiable {

  S state();
  
}
