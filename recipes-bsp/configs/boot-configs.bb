DESCRIPTION = "Generates boot configuration file configs.txt"
LICENSE = "MIT"

S = "${WORKDIR}"

inherit deploy

do_deploy() {
    # Add here variables for inclusion in configs.txt
    DT_OVERLAYS="rock-5a-radxa-camera-4k.dtbo"

    echo "dto_list=${DT_OVERLAYS}" > ${WORKDIR}/configs.txt

    # Deploy
    install -d ${DEPLOYDIR}
    install -m 0644 ${WORKDIR}/configs.txt ${DEPLOYDIR}/
}

addtask deploy before do_build