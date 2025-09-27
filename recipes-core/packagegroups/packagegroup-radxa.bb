DESCRIPTION="Package groups for radxa image"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES += " \
    packagegroup-radxa-base \
    packagegroup-radxa-media \
    packagegroup-radxa-graphics \
"

RDEPENDS:packagegroup-radxa-base = " \
    systemd-analyze \
    i2c-tools \
    libgpiod \
    util-linux \
    nano \
"

RDEPENDS:packagegroup-radxa-media = " \
    v4l-utils \
"

RDEPENDS:packagegroup-radxa-graphics = " \
    libdrm \
    libdrm-tests \
    libdrm-dev \
    libdrm-kms \
"
