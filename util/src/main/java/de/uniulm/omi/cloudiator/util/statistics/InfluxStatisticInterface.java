package de.uniulm.omi.cloudiator.util.statistics;

import com.google.inject.Inject;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfluxStatisticInterface implements StatisticInterface {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(InfluxStatisticInterface.class);

  private final InfluxDB influxDB;
  private static final String DB_NAME = "cloudiator";

  @Inject
  public InfluxStatisticInterface(@Named("influx.url") String url,
      @Named("influx.user") String user,
      @Named("influx.password") String password) {

    LOGGER.info(String
        .format("Connecting to influx server: %s with user: %s and password: %s.", url, user,
            password));

    this.influxDB = InfluxDBFactory.connect(url, user, password);

    if (!influxDB.databaseExists(DB_NAME)) {
      LOGGER.debug(String.format("Database with the name %s does not exist. Creating.", DB_NAME));
      influxDB.createDatabase(DB_NAME);
    }
    influxDB.setDatabase(DB_NAME);
  }

  @Override
  public void reportMetric(Metric metric) {

    final Builder builder = Point.measurement(metric.name())
        .addField("value", metric.value())
        .time(metric.timestamp(), TimeUnit.MILLISECONDS);

    for (Map.Entry<String, String> entry : metric.tags().entrySet()) {
      builder.tag(entry.getKey(), entry.getValue());
    }

    final Point point = builder.build();

    LOGGER.trace(String.format("Writing point %s to influx %s.", point, influxDB));

    influxDB.write(point);
  }
}
