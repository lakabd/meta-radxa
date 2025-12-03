DESCRIPTION = "Rockchip Auto Image Quality libraries & tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

DEPENDS = "coreutils-native xxd-native dos2unix-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES:append = " ${PN}-3A-server ${PN}-fec ${PN}-rawstream ${PN}-smartir"

SRCREV = "73a6010fe79f3185471157d48558fefb04c7e1c4"
SRC_URI = "git://github.com/JeffyCN/mirrors.git;protocol=https;nobranch=1;branch=rkaiq-2023_04_04; \
           file://0001-Fix-build-in-yocto.patch \
		"

S = "${WORKDIR}/git"

inherit pkgconfig cmake

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

	# Image quality files for different sensors (used by rkaiq-3A-server)
	install -d ${D}${sysconfdir}/iqfiles
	case "${RK_ISP_VERSION}" in
		2.0)
			install -m 0644 ${S}/rkaiq/iqfiles/isp20/*.json \
				${D}${sysconfdir}/iqfiles/
			;;
		2.1)
			install -m 0644 ${S}/rkaiq/iqfiles/isp21/*.json \
				${D}${sysconfdir}/iqfiles/
			;;
		3.0)
			install -m 0644 ${S}/rkaiq/iqfiles/isp3x/*.json \
				${D}${sysconfdir}/iqfiles/
			;;
		3.2_LITE)
			install -m 0644 ${S}/rkaiq/iqfiles/isp32_lite/*.json \
				${D}${sysconfdir}/iqfiles/
			;;
	esac
}

FILES:${PN} = "${libdir}/librkaiq.so"
FILES:${PN}-3A-server = "${bindir} ${sysconfdir}"
FILES:${PN}-fec = "${libdir}/libIspFec.so ${datadir}"
FILES:${PN}-rawstream = "${libdir}/librkrawstream.so"
FILES:${PN}-smartir = "${libdir}/libsmartIr.so"

RDEPENDS:${PN}-3A-server = "${PN}"
RDEPENDS:${PN}-fec = "${PN}"
RDEPENDS:${PN}-rawstream = "${PN}"
RDEPENDS:${PN}-smartir = "${PN}"

FILES:${PN}-staticdev = "${libdir}/librkaiq.a ${libdir}/libIspFec.a ${libdir}/libsmartIr.a"
FILES:${PN}-dev = "${includedir}"