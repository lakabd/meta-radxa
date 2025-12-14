DESCRIPTION = "Userspace Mali GPU drivers for Rockchip SoCs"
SECTION = "libs"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://END_USER_LICENCE_AGREEMENT.txt;md5=3918cc9836ad038c5a090a0280233eea"

SRCREV = "309268f7a34ca0bba0ab94a0b09feb0191c77fb8"
SRCBRANCH = "libmali"
SRC_URI = "git://github.com/JeffyCN/mirrors.git;protocol=https;branch=${SRCBRANCH};"

S = "${WORKDIR}/git"

DEPENDS = "coreutils-native libdrm"

PACKAGES = "libmali libmali-dev libmali-dbg libmali-staticdev \
		    libegl libegl-dev libgles1 libgles1-dev libgles2 libgles2-dev \
			libgles3 libgles3-dev libgbm libgbm-dev libopencl libopencl-dev"

PROVIDES = "virtual/egl virtual/libgles1 virtual/libgles2 virtual/libgles3 virtual/libgbm virtual/libopencl"

MALI_GPU ?= "valhall-g610"
MALI_VERSION ?= "g6p0"
MALI_SUBVERSION ?= "none"
MALI_PLATFORM ?= "gbm"

# The ICD OpenCL implementation should work with opencl-icd-loader.
RDEPENDS:libopencl:append = " opencl-icd-loader"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit meson pkgconfig

EXTRA_OEMESON = " \
	-Dgpu=${MALI_GPU} \
	-Dversion=${MALI_VERSION} \
	-Dsubversion=${MALI_SUBVERSION} \
	-Dplatform=${MALI_PLATFORM} \
"

INSANE_SKIP:${PN}:append = " already-stripped"
INSANE_SKIP:libmali:append = " ldflags"

FILES:libmali = "${libdir}/libmali*.so.* ${nonarch_base_libdir}/firmware"
FILES:libmali-staticdev = "${libdir}/libmali*.a"
FILES:libmali-dev = "${includedir}/KHR ${libdir}/libmali*.so* ${libdir}/pkgconfig/mali.pc"
FILES:libmali-dbg = "${libdir}/.debug"

FILES:libegl = "${libdir}/libEGL.so.*"
FILES:libegl-dev = "${libdir}/libEGL* ${includedir}/EGL ${libdir}/pkgconfig/egl.pc"
RDEPENDS:libegl += "libmali"
RDEPENDS:libegl-dev += "libmali-dev"

FILES:libgles1 = "${libdir}/libGLESv1*.so.*"
FILES:libgles1-dev = "${libdir}/libGLESv1* ${includedir}/GLES ${libdir}/pkgconfig/glesv1*.pc"
RDEPENDS:libgles1 += "libmali"
RDEPENDS:libgles1-dev += "libmali-dev"

FILES:libgles2 = "${libdir}/libGLESv2.so.*"
FILES:libgles2-dev = "${libdir}/libGLESv2* ${includedir}/GLES2 ${libdir}/pkgconfig/glesv2.pc"
RDEPENDS:libgles2 += "libmali"
RDEPENDS:libgles2-dev += "libmali-dev"

ALLOW_EMPTY:libgles3 = "1"
FILES:libgles3-dev = "${includedir}/GLES3"
RDEPENDS:libgles3 += "libmali libgles2"
RDEPENDS:libgles3-dev += "libmali-dev libgles2-dev"

FILES:libgbm = "${libdir}/libgbm.so.*"
FILES:libgbm-dev = "${libdir}/libgbm* ${includedir}/gbm.h ${libdir}/pkgconfig/gbm.pc"
RDEPENDS:libgbm += "libmali"
RDEPENDS:libgbm-dev += "libmali-dev"

FILES:libopencl = "${libdir}/libMaliOpenCL.so.* ${sysconfdir}"
FILES:libopencl-dev = "${libdir}/libMaliOpenCL*"
RDEPENDS:libopencl += "libmali"
RDEPENDS:libopencl-dev += "libmali-dev"
