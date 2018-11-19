package de.uniulm.omi.cloudiator.util.statistics;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import java.util.Map;

public class Metric {

  private final String name;
  private final Number value;
  private final long timestamp;
  private final Map<String, String> tags;

  Metric(String name, Number value, long timestamp,
      Map<String, String> tags) {

    checkNotNull(name, "name is null");
    checkArgument(!name.isEmpty(), "name is empty");

    this.name = name;

    checkNotNull(value, "value is null");
    this.value = value;

    this.timestamp = timestamp;

    checkNotNull(tags, "tags is null");
    this.tags = tags;
  }

  public String name() {
    return name;
  }

  public Number value() {
    return value;
  }

  public long timestamp() {
    return timestamp;
  }

  public Map<String, String> tags() {
    return tags;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("name", name).add("value", value)
        .add("timestamp", timestamp).add("tags", tags).toString();
  }
}
