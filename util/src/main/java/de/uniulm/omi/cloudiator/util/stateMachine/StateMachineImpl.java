package de.uniulm.omi.cloudiator.util.stateMachine;

import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import org.jgrapht.GraphPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateMachineImpl<O extends Stateful> implements StateMachine<O> {

  private static final String TRANSITION_NOT_FOUND = "Fatal error. Could not find transition from state %s to state %s while handling transition for object %s.";


  private static final Logger LOGGER =
      LoggerFactory.getLogger(StateMachineImpl.class);

  private final Set<StateMachineHook<O>> hooks;
  private final TransitionGraph<O> transitionGraph;
  @Nullable
  private final State errorState;


  public StateMachineImpl(
      Set<Transition<O>> transitions,
      Set<StateMachineHook<O>> hooks, @Nullable State errorState) {
    this.hooks = hooks;
    transitionGraph = TransitionGraph.of(transitions);
    this.errorState = errorState;
  }

  private void preStateTransition(O object, State to) {
    for (StateMachineHook<O> hook : hooks) {
      hook.pre(object, to);
    }
  }

  private void postStateTransition(O object, State from) {
    for (StateMachineHook<O> hook : hooks) {
      hook.post(from, object);
    }
  }

  @Override
  public O apply(O object, State to, Object[] arguments) throws ExecutionException {

    if (object.state().equals(to)) {
      LOGGER.info(
          String.format("Object %s is already in the to state %s. Doing nothing.", object, to));
      return object;
    }

    LOGGER.info(String.format("State transition of object %s to state %s", object, to));

    //call hooks
    LOGGER.debug(
        String.format("Calling pre Transition hooks for object %s to state %s.", object, to));
    preStateTransition(object, to);

    //calculate the shortest path
    final Optional<GraphPath<State, Transition<O>>> path = transitionGraph
        .shortestPath(object.state(), to);

    if (!path.isPresent()) {
      final String errorMessage = String.format(
          TRANSITION_NOT_FOUND,
          object.state(), to, object);
      throw new ExecutionException(new IllegalStateException(errorMessage));
    }

    LOGGER
        .debug(String
            .format("Calculated the path %s from state %s to state %s.", path.get(), object.state(),
                to));

    for (Transition<O> transition : path.get().getEdgeList()) {
      try {
        object = traverse(object, arguments, transition);
      } catch (ExecutionException e) {
        if (errorState != null) {
          LOGGER.warn(String.format(
              "Error while traversing from state %s to state %s for object %s. Starting transition to error state %s.",
              object.state(), to, object, errorState));
          apply(object, errorState, arguments);
        } else {
          throw new ExecutionException(String
              .format("Error while traversing from state %s to state %s for object %s.",
                  object.state(), to, object), e.getCause());
        }
      }
    }

    return object;

  }

  private O traverse(O object, Object[] arguments, Transition<O> transition)
      throws ExecutionException {

    final State previousState = object.state();

    checkState(object.state().equals(transition.from()), String
        .format("Transition expects object to be in state %s but object is in state %s.",
            transition.from(), object.state()));

    final O changedObject = transition.apply(object, arguments);

    checkState(changedObject.state().equals(transition.to()), String.format(
        "Transition expected object to be in state %s after execution. It is however in state %s.",
        changedObject.state(), transition.to()));

    //call hooks
    LOGGER.debug(
        String.format("Calling post Transition hooks for object %s from state %s.", changedObject,
            previousState));
    postStateTransition(changedObject, previousState);

    return changedObject;
  }


}
