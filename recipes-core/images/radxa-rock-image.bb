SUMMARY = "My Image for Radxa Rock"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit core-image

EXTRA_IMAGEDEPENDS += "virtual/bootloader virtual/dtb boot-configs"

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL += " \
    packagegroup-radxa-base \
    packagegroup-radxa-media \
    packagegroup-radxa-graphics \
"

IMAGE_BOOT_FILES = "u-boot.${UBOOT_SUFFIX} ${SPL_BINARY} ${SOC_FAMILY}-${MACHINE}.dtb configs.txt devicetree/*;overlays/"