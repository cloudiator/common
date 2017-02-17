/*
 * Copyright 2017 University of Ulm
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

package de.uniulm.omi.cloudiator.domain;

import com.google.common.base.CaseFormat;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.03.16.
 */
public enum OperatingSystemFamily implements RemotePortProvider, LoginNameSupplier {

    UNKNOWN,

    /**
     * IBM AIX
     */
    AIX, /**
     * Arch Linux
     */
    ARCH, /**
     * Centos
     */
    CENTOS(OperatingSystemType.LINUX, OperatingSystemVersionFormats.set(2,3,4,5,6,7),
            LoginNameSuppliers.staticSupplier("centos"), null), /**
     * Darwin OS
     */
    DARWIN, DEBIAN, /**
     * VMWare ESX
     */
    ESX, FEDORA, FREEBSD, GENTOO, /**
     * Hewlett Packard Unix
     */
    HPUX, COREOS, /**
     * Amazon Linux
     */
    AMZN_LINUX, MANDRIVA, NETBSD, /**
     * Oracle Linux OS
     */
    OEL, OPENBSD, /**
     * Red Hat Enterprise Linux
     */
    RHEL, SCIENTIFIC, CEL, SLACKWARE, SOLARIS, SUSE, TURBOLINUX, CLOUD_LINUX, UBUNTU(
        OperatingSystemType.LINUX,
        OperatingSystemVersionFormats.supplier(new UbuntuOperatingSystemVersionSupplier()),
        LoginNameSuppliers.staticSupplier("ubuntu"), new UbuntuUrlDownloadFunction()), WINDOWS(
        OperatingSystemType.WINDOWS, OperatingSystemVersionFormats.unknown(),
        LoginNameSuppliers.staticSupplier("administrator"), null);

    private static final OperatingSystemFamily DEFAULT = UNKNOWN;
    private final OperatingSystemType operatingSystemType;
    private final OperatingSystemVersionFormat operatingSystemVersionFormat;
    private final LoginNameSupplier loginNameSupplier;
    @Nullable private final DownloadUrlFunction downloadUrlFunction;

    OperatingSystemFamily(OperatingSystemType operatingSystemType,
        OperatingSystemVersionFormat operatingSystemVersionFormat,
        LoginNameSupplier loginNameSupplier, @Nullable DownloadUrlFunction downloadUrlFunction) {
        this.operatingSystemType = operatingSystemType;
        this.operatingSystemVersionFormat = operatingSystemVersionFormat;
        this.loginNameSupplier = loginNameSupplier;
        this.downloadUrlFunction = downloadUrlFunction;
    }

    OperatingSystemFamily() {
        this.operatingSystemType = OperatingSystemType.DEFAULT;
        this.operatingSystemVersionFormat = OperatingSystemVersionFormats.unknown();
        this.loginNameSupplier = LoginNameSuppliers.nullSupplier();
        this.downloadUrlFunction = null;
    }

    public String value() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, this.name());
    }

    @Override public int remotePort() {
        return operatingSystemType.remotePort();
    }

    public OperatingSystemVersionFormat operatingSystemVersionFormat() {
        return operatingSystemVersionFormat;
    }

    public static OperatingSystemFamily fromValue(String operatingSystemFamily) {
        checkNotNull(operatingSystemFamily);
        try {
            return valueOf(
                CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, operatingSystemFamily));
        } catch (IllegalArgumentException e) {
            return DEFAULT;
        }
    }

    public OperatingSystemType operatingSystemType() {
        return operatingSystemType;
    }

    public Optional<DownloadUrlFunction> downloadUrlFunction() {
        return Optional.ofNullable(downloadUrlFunction);
    }

    @Override public String loginName() {
        return loginNameSupplier.loginName();
    }
}
