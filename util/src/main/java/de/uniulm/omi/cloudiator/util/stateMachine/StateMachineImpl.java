package de.uniulm.omi.cloudiator.util.stateMachine;

import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import org.jgrapht.GraphPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateMachineImpl<O extends Stateful> implements StateMachine<O>,
    ErrorAwareStateMachine<O> {

  private static final String TRANSITION_NOT_FOUND = "Fatal error. Could not find transition from state %s to state %s.";


  private static final Logger LOGGER =
      LoggerFactory.getLogger(StateMachineImpl.class);

  private final Set<StateMachineHook<O>> hooks;
  private final TransitionGraph<O> transitionGraph;
  @Nullable
  private final ErrorTransition<O> errorTransition;


  public StateMachineImpl(
      Set<Transition<O>> transitions,
      Set<StateMachineHook<O>> hooks,
      @Nullable ErrorTransition<O> errorTransition) {
    this.hooks = hooks;
    transitionGraph = TransitionGraph.of(transitions);
    this.errorTransition = errorTransition;
  }

  private void preStateTransition(O object, State to) {

    LOGGER.debug(
        String.format("Calling pre Transition hooks for object %s to state %s.", object, to));

    for (StateMachineHook<O> hook : hooks) {
      hook.pre(object, to);
    }
  }

  private void postStateTransition(O object, State from) {

    LOGGER.debug(
        String.format("Calling post Transition hooks for object %s from state %s.", object,
            from));

    for (StateMachineHook<O> hook : hooks) {
      hook.post(from, object);
    }
  }

  @Override
  public O apply(O object, State to, Object[] arguments) {

    if (object.state().equals(to)) {
      LOGGER.info(
          String.format("Object %s is already in the to state %s. Doing nothing.", object, to));
      return object;
    }

    LOGGER.info(String.format("State transition of object %s to state %s", object, to));

    //check if we are executing to the error state
    if (errorTransition != null && to.equals(errorTransition.errorState())) {
      return error(object, arguments, null);
    }
    return normal(object, to, arguments);

  }

  private GraphPath<State, Transition<O>> calculatePath(State from, State to) {

    //calculate the shortest path
    final Optional<GraphPath<State, Transition<O>>> path = transitionGraph
        .shortestPath(from, to);

    if (!path.isPresent()) {
      final String errorMessage = String.format(
          TRANSITION_NOT_FOUND,
          from, to);
      throw new IllegalStateException(errorMessage);
    }

    LOGGER
        .debug(String
            .format("Calculated the path %s from state %s to state %s.", path.get(), from,
                to));

    return path.get();

  }


  private O traverse(O object, Object[] arguments, Transition<O> transition)
      throws ExecutionException {

    final State previousState = object.state();

    //call pre hooks
    preStateTransition(object, transition.to());

    checkState(object.state().equals(transition.from()), String
        .format("Transition expects object to be in state %s but object is in state %s.",
            transition.from(), object.state()));

    final O changedObject = transition.apply(object, arguments);

    checkState(changedObject.state().equals(transition.to()), String.format(
        "Transition expected object to be in state %s after execution. It is however in state %s.",
        changedObject.state(), transition.to()));

    //call hooks
    postStateTransition(changedObject, previousState);

    return changedObject;
  }


  private O error(O object, Object[] arguments, @Nullable Throwable t) {

    final State previousState = object.state();

    checkState(errorTransition != null, "Can not fail as no error transition is set.");

    preStateTransition(object, errorTransition.errorState());

    final O changedObject = errorTransition.apply(object, arguments, t);

    checkState(changedObject.state().equals(errorTransition.errorState()), String
        .format("Transition expected object %s to be in error state %s. But object is in state %s.",
            object, errorTransition.errorState(), object.state()));

    postStateTransition(changedObject, previousState);

    return changedObject;
  }

  private O normal(O object, State to, Object[] arguments) {

    final GraphPath<State, Transition<O>> graphPath = calculatePath(object.state(),
        to);

    for (Transition<O> transition : graphPath.getEdgeList()) {

      try {
        object = traverse(object, arguments, transition);
      } catch (ExecutionException e) {
        if (errorTransition != null) {
          LOGGER.warn(String.format(
              "Error while traversing from state %s to state %s for object %s. Starting transition to error state %s.",
              object.state(), to, object, errorTransition.errorState()),e);
          fail(object, arguments, e.getCause());
        } else {
          throw new IllegalStateException(String
              .format("Error while traversing from state %s to state %s for object %s.",
                  object.state(), to, object), e.getCause());
        }
      }
    }

    return object;
  }


  @Override
  public O fail(O object, Object[] arguments, Throwable t) {
    return error(object, arguments, t);
  }
}
