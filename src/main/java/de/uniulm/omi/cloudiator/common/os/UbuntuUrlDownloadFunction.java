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

import de.uniulm.omi.cloudiator.domain.OperatingSystemArchitecture;
import de.uniulm.omi.cloudiator.domain.OperatingSystemVersion;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 01.08.16.
 */
public class UbuntuUrlDownloadFunction implements DownloadUrlFunction {

    private final static String UBUNTU_CLOUD_IMAGES_SITE =
        "https://cloud-images.ubuntu.com/daily/server/releases/%1$s/release/ubuntu-%1$s-server-cloudimg-%2$s.%3$s";

    @Override public URL generateURL(OperatingSystemArchitecture operatingSystemArchitecture,
        OperatingSystemVersion operatingSystemVersion, ImageFormat imageFormat) {

        checkState(operatingSystemVersion.name().isPresent(),
            "Name of the version is not present.");

        try {
            return new URL(String.format(UBUNTU_CLOUD_IMAGES_SITE, operatingSystemVersion.name(),
                new ArchitectureToStringConverter().apply(operatingSystemArchitecture),
                imageFormat.fileNameExtension()));
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }

    }

    private static class ArchitectureToStringConverter
        implements Function<OperatingSystemArchitecture, String> {

        @Override public String apply(OperatingSystemArchitecture operatingSystemArchitecture) {
            if (operatingSystemArchitecture.equals(OperatingSystemArchitecture.UNKNOWN)) {
                throw new IllegalStateException("operatingSystemArchitecture is unknown.");
            }
            return operatingSystemArchitecture.toString().toLowerCase();
        }
    }
}
