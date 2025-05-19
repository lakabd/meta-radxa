FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# xxd: not found
DEPENDS += "vim-native"

SRC_URI:append = " \
    file://extra-configs.cfg \
    file://env.cfg;subdir=git/ \
"