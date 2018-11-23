package de.uniulm.omi.cloudiator.util.statistics;

public class NullStatisticInterface implements StatisticInterface {

  @Override
  public void reportMetric(Metric metric) {
    //intentionally left empty
  }
}
