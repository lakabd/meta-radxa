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
    packagegroup-core-buildessential \
    packagegroup-core-tools-debug \
    systemd-analyze \
    i2c-tools \
    libgpiod \
    util-linux \
    nano \
    e2fsprogs-mke2fs \
    e2fsprogs-resize2fs \
    gptfdisk \
    parted \
    glibc-utils \
    first-boot \
"

RDEPENDS:packagegroup-radxa-media = " \
    v4l-utils \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    rockchip-rkaiq-3A-server \
    camcap \
"

RDEPENDS:packagegroup-radxa-graphics = " \
    libdrm \
    libdrm-tests \
    libdrm-dev \
    libdrm-kms \
    rockchip-libmali \
"
