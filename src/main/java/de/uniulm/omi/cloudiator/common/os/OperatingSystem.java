package de.uniulm.omi.cloudiator.common.os;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by daniel on 09.03.16.
 */
public interface OperatingSystem {

    @JsonProperty
    OperatingSystemFamily operatingSystemFamily();

    @JsonProperty
    OperatingSystemArchitecture operatingSystemArchitecture();

    @JsonProperty
    OperatingSystemVersion operatingSystemVersion();
}
