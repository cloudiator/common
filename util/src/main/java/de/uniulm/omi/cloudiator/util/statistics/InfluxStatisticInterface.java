package de.uniulm.omi.cloudiator.util.statistics;

import com.google.inject.Inject;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

public class InfluxStatisticInterface implements StatisticInterface {

  private final InfluxDB influxDB;
  private static final String DB_NAME = "cloudiator";

  @Inject
  public InfluxStatisticInterface(@Named("influx.url") String url,
      @Named("influx.user") String user,
      @Named("influx.password") String password) {
    this.influxDB = InfluxDBFactory.connect(url, user, password);

    if (!influxDB.databaseExists(DB_NAME)) {
      influxDB.createDatabase(DB_NAME);
    }
    influxDB.setDatabase(DB_NAME);

  }

  @Override
  public void reportMetric(Metric metric) {

    final Builder builder = Point.measurement(metric.name())
        .addField("value", metric.value().toString())
        .time(metric.timestamp(), TimeUnit.MILLISECONDS);

    for (Map.Entry<String, String> entry : metric.tags().entrySet()) {
      builder.tag(entry.getKey(), entry.getValue());
    }

    influxDB.write(builder.build());
  }
}
