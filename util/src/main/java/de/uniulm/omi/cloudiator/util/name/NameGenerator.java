package de.uniulm.omi.cloudiator.util.name;

import static com.google.common.base.Preconditions.checkNotNull;

import de.uniulm.omi.cloudiator.util.RandomSet;
import java.util.Random;
import java.util.Set;

public class NameGenerator {

  private final RandomSet<String> adjectives;
  private final RandomSet<String> names;
  private static final Random RANDOM = new Random();

  public NameGenerator(Set<String> adjectives, Set<String> names) {
    checkNotNull(adjectives, "adjectives is null");
    checkNotNull(names, "names is null");

    this.adjectives = new RandomSet<>(adjectives);
    this.names = new RandomSet<>(names);
  }

  public String generateName() {
    
    String adjective = adjectives.pollRandom(RANDOM);
    String name = names.pollRandom(RANDOM);
    checkNotNull(name, "name is null");

    if (adjective != null) {
      return adjective + "." + name;
    }
    return name;

  }

}
