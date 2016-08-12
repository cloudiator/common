/*
 * Copyright 2016 University of Ulm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uniulm.omi.cloudiator.common.os;

import com.google.common.collect.Lists;

import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by daniel on 25.07.16.
 */
public class UbuntuOperatingSystemVersionSupplier implements Supplier<Set<OperatingSystemVersion>> {

    private final static Year FIRST_YEAR = Year.of(2004);
    private final List<Month> releaseMonths;
    private final Month FIRST_MONTH = Month.OCTOBER;

    public UbuntuOperatingSystemVersionSupplier() {
        releaseMonths = Lists.newArrayList(Month.APRIL, Month.OCTOBER);
    }

    @Override public Set<OperatingSystemVersion> get() {
        Set<OperatingSystemVersion> operatingSystemVersions = new HashSet<>();

        for (Year year = FIRST_YEAR;
             year.compareTo(Year.now()) < 1; year = year.plus(1, ChronoUnit.YEARS)) {
            for (final Month month : releaseMonths) {
                if (year.equals(FIRST_YEAR) && month.compareTo(FIRST_MONTH) < 0) {
                    continue;
                }
                //exception for dapper drake
                if (year.equals(Year.of(2006))) {
                    if (month.equals(Month.APRIL)) {
                        operatingSystemVersions.add(of(year, Month.JUNE));
                        continue;
                    }
                }
                operatingSystemVersions.add(of(year, month));
            }
        }
        return operatingSystemVersions;
    }

    private static OperatingSystemVersion of(Year year, Month month) {

        int parseInt =
            Integer.parseInt(String.format("%s%02d", year.getValue() - 2000, month.getValue()));
        String parseString = String.format("%s.%02d", year.getValue() - 2000, month.getValue());

        return OperatingSystemVersion.of(parseInt, parseString);
    }
}
