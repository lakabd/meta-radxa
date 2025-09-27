DESCRIPTION="Adds a service for first boot setup e.g., partitioning"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit systemd

RDEPENDS:${PN} += "bash"

SRC_URI = " \
    file://first-boot.sh \
    file://first-boot.service \
    file://home-root.mount \
"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${bindir}
    install -m 755 ${WORKDIR}/first-boot.sh ${D}${bindir}/
    install -m 644 ${WORKDIR}/first-boot.service ${D}${systemd_system_unitdir}/
    install -m 644 ${WORKDIR}/home-root.mount ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE:${PN} = "first-boot.service home-root.mount"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"