DESCRIPTION = "Camera capture utility for V4L2 devices"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=1283c8bf40e22a97dbe1571ea31f1892"


inherit cmake pkgconfig

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/lakabd/camcap.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=Release"

FILES:${PN} = "${bindir}"