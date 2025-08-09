SUMMARY = "My Image for Radxa Rock"
LICENSE = "MIT"

inherit core-image

EXTRA_IMAGEDEPENDS += "virtual/bootloader"

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_BOOT_FILES = "u-boot.${UBOOT_SUFFIX} ${SPL_BINARY} ${SOC_FAMILY}-${MACHINE}.dtb"