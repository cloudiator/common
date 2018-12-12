package de.uniulm.omi.cloudiator.domain;

import com.google.common.collect.Lists;

import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class AmazonLinuxOperatingSystemVersionSupplier implements Supplier<Set<OperatingSystemVersion>> {

  private final static Year FIRST_YEAR = Year.of(2011);
  private final List<Month> releaseMonths;
  private final Month FIRST_MONTH = Month.SEPTEMBER;

  public AmazonLinuxOperatingSystemVersionSupplier() {
    releaseMonths = Lists.newArrayList(Month.MARCH, Month.SEPTEMBER);
  }

  private static OperatingSystemVersion of(Year year, Month month) {

    int parseInt =
        Integer.parseInt(String.format("%s%02d", year.getValue(), month.getValue()));
    String parseString = String.format("%s.%02d", year.getValue(), month.getValue());

    return OperatingSystemVersions.of(parseInt, parseString);
  }

  @Override
  public Set<OperatingSystemVersion> get() {
    Set<OperatingSystemVersion> operatingSystemVersions = new HashSet<>();

    for (Year year = FIRST_YEAR;
         year.compareTo(Year.now()) < 1; year = year.plus(1, ChronoUnit.YEARS)) {
      for (final Month month : releaseMonths) {
        if (year.equals(FIRST_YEAR) && month.compareTo(FIRST_MONTH) < 0) {
          continue;
        }
        operatingSystemVersions.add(of(year, month));
      }
    }
    return operatingSystemVersions;
  }
}
