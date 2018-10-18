package de.uniulm.omi.cloudiator.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Strings;

public class GenericNameBasedDockerImageFunction implements DockerImageFunction {

  private final String name;
  private final static String SEPARATOR = ":";

  public GenericNameBasedDockerImageFunction(String name) {
    checkArgument(!Strings.isNullOrEmpty(name), "name is null or empty");
    this.name = name;
  }


  @Override
  public String apply(OperatingSystemVersion operatingSystemVersion) {
    checkNotNull(operatingSystemVersion, "operatingSystemVersion is null");
    checkArgument(operatingSystemVersion.name().isPresent(),
        "operatingSystemVersion name is not present");

    return name + SEPARATOR + operatingSystemVersion.name()
        .get();
  }
}
