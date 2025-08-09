# Enable login banner
do_install:prepend () {
    # after do_configure sshd_config is in ${B}
    sed -i -e 's:#Banner none:Banner /etc/issue.net:' ${B}/sshd_config
}