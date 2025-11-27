DESCRIPTION = "Userspace Mali GPU drivers for Rockchip SoCs"
SECTION = "libs"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://END_USER_LICENCE_AGREEMENT.txt;md5=3918cc9836ad038c5a090a0280233eea"

SRCREV = "309268f7a34ca0bba0ab94a0b09feb0191c77fb8"
SRCBRANCH = "libmali"
SRC_URI = "git://github.com/JeffyCN/mirrors.git;protocol=https;branch=${SRCBRANCH};"

S = "${WORKDIR}/git"

DEPENDS = "coreutils-native libdrm"

PROVIDES:append = " virtual/egl virtual/libgles1 virtual/libgles2 virtual/libgles3 virtual/libgbm"
RPROVIDES:${PN}:append = " libmali"

MALI_GPU ?= "valhall-g610"
MALI_VERSION ?= "g6p0"
MALI_SUBVERSION ?= "none"
MALI_PLATFORM ?= "gbm"

# The ICD OpenCL implementation should work with opencl-icd-loader.
RDEPENDS:${PN}:append = " opencl-icd-loader"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit meson pkgconfig

EXTRA_OEMESON = " \
	-Dgpu=${MALI_GPU} \
	-Dversion=${MALI_VERSION} \
	-Dsubversion=${MALI_SUBVERSION} \
	-Dplatform=${MALI_PLATFORM} \
"

INSANE_SKIP:${PN}:append = " already-stripped ldflags"

FILES:${PN} = " \
    ${sysconfdir} \
    ${nonarch_base_libdir} \
	${includedir} \
	${libdir} \
"
