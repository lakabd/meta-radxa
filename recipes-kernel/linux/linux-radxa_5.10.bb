# Released under the MIT license (see COPYING.MIT for the terms)
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

require recipes-kernel/linux/linux-yocto.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

# use default kconfig values when not specified explicitly
KCONFIG_MODE ?= "--alldefconfig"

LINUX_VERSION = "5.10.110"
PV = "${LINUX_VERSION}"

LINUX_KERNEL_TYPE = "standard"
LINUX_VERSION_EXTENSION ?= "-rockchip-${LINUX_KERNEL_TYPE}"

SRCREV = "${AUTOREV}"
SRCBEANCH = "linux-5.10-gen-rkr3.4"
SRC_URI = " \
	git://github.com/radxa/kernel.git;protocol=https;branch=${SRCBEANCH}; \
	file://rockchip-kmeta/bsp/wifi.cfg \
"
