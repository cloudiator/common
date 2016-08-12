package de.uniulm.omi.cloudiator.common.os;

/**
 * Created by daniel on 09.03.16.
 */
public interface OperatingSystem {

    OperatingSystemFamily operatingSystemFamily();

    OperatingSystemArchitecture operatingSystemArchitecture();

    OperatingSystemVersion operatingSystemVersion();
}
