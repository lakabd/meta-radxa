DESCRIPTION = "Rockchip Auto Image Quality library & tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

DEPENDS = "coreutils-native xxd-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES:append = " ${PN}-3A-server"

SRCREV = "58c7b5843b2d975cf8f751a795b09ecc126e9f09"
SRC_URI = " \
		git://github.com/JeffyCN/mirrors.git;protocol=https;nobranch=1;branch=rkaiq-2022_09_22; \
		file://0001-Fix-build-in-yocto.patch \
		file://imx415_RADXA-CAMERA-4K_DEFAULT.json \
		file://rkaiq_3A.service \
"

S = "${WORKDIR}/git"

inherit pkgconfig cmake systemd

EXTRA_OECMAKE = "     \
    -DARCH=${@bb.utils.contains('TUNE_FEATURES', 'aarch64', 'aarch64', 'arm', d)} \
    -DISP_HW_VERSION=-DISP_HW_V${@d.getVar('RK_ISP_VERSION').replace('.','')} \
    -DRKAIQ_TARGET_SOC=${@d.getVar('SOC_FAMILY').replace('rk3588s','rk3588')} \
"

# Fix CRLF endings on files to patch
fix_line_endings() {
    sed -i 's/\r$//' "${S}/rkaiq_3A_server/CMakeLists.txt"
}
do_patch[prefuncs] += "fix_line_endings"

do_install:append () {
	# Image quality file for Radxa Camera 4K (on rk3588s i.e. ISP version 3.0)
	install -d ${D}${sysconfdir}/iqfiles
	install -m 0644 ${WORKDIR}/imx415_RADXA-CAMERA-4K_DEFAULT.json ${D}${sysconfdir}/iqfiles/

	# 3A server service file
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/rkaiq_3A.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN}-3A-server = "rkaiq_3A.service"

FILES:${PN} = "${libdir}/librkaiq.so"
FILES:${PN}-dev = "${includedir}"
FILES:${PN}-3A-server = "${bindir} ${sysconfdir} ${systemd_system_unitdir}"
RDEPENDS:${PN}-3A-server = "${PN}"