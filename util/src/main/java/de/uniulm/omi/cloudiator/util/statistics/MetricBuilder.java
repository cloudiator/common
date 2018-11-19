package de.uniulm.omi.cloudiator.util.statistics;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

public class MetricBuilder {

  private String name;
  private Number value;
  private long timestamp;
  private Map<String, String> tags;

  private MetricBuilder() {
    tags = new HashMap<>();
  }

  public static MetricBuilder create() {
    return new MetricBuilder();
  }

  public MetricBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MetricBuilder value(Number value) {
    this.value = value;
    return this;
  }

  public MetricBuilder timestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public MetricBuilder now() {
    this.timestamp = System.currentTimeMillis();
    return this;
  }

  public MetricBuilder addTag(String key, String value) {
    this.tags.put(key, value);
    return this;
  }

  public Metric build() {
    return new Metric(name, value, timestamp, ImmutableMap.copyOf(tags));
  }

}
