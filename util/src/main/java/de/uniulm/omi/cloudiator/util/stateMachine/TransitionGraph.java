package de.uniulm.omi.cloudiator.util.stateMachine;

import java.util.Optional;
import java.util.Set;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransitionGraph<O extends Stateful> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(TransitionGraph.class);
  private final DefaultDirectedGraph<State, Transition> graph;

  public static <T extends Stateful> TransitionGraph<T> of(Set<Transition<T>> transitions) {
    return new TransitionGraph<>(transitions);
  }

  private TransitionGraph(Set<Transition<O>> transitions) {

    graph = new DefaultDirectedGraph<>(
        Transition.class);

    for (Transition<O> transition : transitions) {
      graph.addVertex(transition.from());
      graph.addVertex(transition.to());
      graph.addEdge(transition.from(), transition.to(), transition);
    }

    LOGGER.debug(String.format("Build transition graph %s.", graph));

  }

  public Optional<GraphPath<State, Transition<O>>> shortestPath(State source, State sink) {
    return Optional.ofNullable(new DijkstraShortestPath(graph).getPath(source, sink));
  }


}
