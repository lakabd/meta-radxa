HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native python3-setuptools-native gnutls-native python3-pyelftools-native vim-native"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

require recipes-bsp/u-boot/u-boot.inc
inherit pkgconfig

PROVIDES = "virtual/bootloader"

SRCREV = "3d8be1f5ec30180748259a251efe4f63c8b4b329"
SRCREV_rkbin = "f43a462e7a1429a9d407ae52b4745033034a6cf9"
SRC_URI = " \
        git://github.com/u-boot/u-boot.git;protocol=https;branch=master \
        git://github.com/rockchip-linux/rkbin.git;protocol=https;branch=master;name=rkbin;destsuffix=rkbin; \
        file://extra-configs.cfg \
        file://env.cfg;subdir=git/ \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

do_compile:prepend() {
    export BL31=${WORKDIR}/rkbin/bin/rk35/rk3588_bl31_v1.48.elf
    export ROCKCHIP_TPL=${WORKDIR}/rkbin/bin/rk35/rk3588_ddr_lp4_2112MHz_lp5_2400MHz_v1.18.bin
}