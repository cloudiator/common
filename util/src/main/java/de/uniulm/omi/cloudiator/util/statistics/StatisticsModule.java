package de.uniulm.omi.cloudiator.util.statistics;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsModule extends AbstractModule {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(StatisticsModule.class);

  private final StatisticsContext statisticsContext;

  public StatisticsModule(StatisticsContext statisticsContext) {
    this.statisticsContext = statisticsContext;
  }

  @Override
  protected void configure() {

    if (statisticsContext.enabled() != null && statisticsContext.enabled()) {

      LOGGER.info("Enabling statistics interface.");

      bind(StatisticInterface.class).to(InfluxStatisticInterface.class);
      bindConstant().annotatedWith(Names.named("influx.url")).to(statisticsContext.url());
      bindConstant().annotatedWith(Names.named("influx.user")).to(statisticsContext.user());
      bindConstant().annotatedWith(Names.named("influx.password")).to(statisticsContext.password());
    } else {
      LOGGER.info("Disabling statistics interface.");
      bind(StatisticInterface.class).to(NullStatisticInterface.class);
    }
  }
}
