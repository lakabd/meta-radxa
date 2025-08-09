FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = "\
    file://eth0.network \
"

do_install(){
    install -D -m0644 ${WORKDIR}/eth0.network ${D}${systemd_unitdir}/network/20-eth0.network
}