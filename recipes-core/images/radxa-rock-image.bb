SUMMARY = "My Image for Radxa Rock"
LICENSE = "MIT"

inherit core-image

EXTRA_IMAGEDEPENDS += "virtual/bootloader virtual/dtb boot-configs"

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL += " \
        v4l-utils \
        i2c-tools \
        libgpiod \
        libdrm \
        libdrm-tests \
        libdrm-dev \
        libdrm-kms \
"

IMAGE_BOOT_FILES = "u-boot.${UBOOT_SUFFIX} ${SPL_BINARY} ${SOC_FAMILY}-${MACHINE}.dtb configs.txt devicetree/*;overlays/"