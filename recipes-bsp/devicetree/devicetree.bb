SUMMARY = "Devicetree"
DESCRIPTION = "Merge one or multiple external dt overlays with one in-tree base dtb"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

COMPATIBLE_MACHINE = ".*"

inherit devicetree

SRC_URI = "file://rock-5a-radxa-camera-4k.dts"

BASE_DTS = ""


# TODO: Below functionnality can be very usefull. To be submitted to mainline.
# Variable to specify the base device tree to merge overlays into.
BASE_DTS[doc] = "The base device tree (from the kernel tree) to apply overlays to. If set, all compiled .dtbo files will be merged into this base."
BASE_DTS ??= ""

def devicetree_merge_overlays(d):
    import subprocess, glob

    base_dts = d.getVar("BASE_DTS")
    if not base_dts:
        return # Do nothing if BASE_DTS is not set

    bb.note(f"BASE_DTS is set to '{base_dts}'. Merging overlays.")

    b_dir = d.getVar("B")
    base = os.path.basename(base_dts).split('.')[0]
    base_dtb = os.path.join(b_dir, f"{base}.dtb")
    merged_dtb_name = f"{base}-merged.dtb"
    merged_dtb_path = os.path.join(b_dir, merged_dtb_name)
    includes = expand_includes("DT_INCLUDE", d)
    
    # Find the full path to the base DTS file in the include paths
    base_dts_path = ""
    for include_path in includes:
        path_to_check = os.path.join(include_path, base_dts)
        if os.path.isfile(path_to_check):
            base_dts_path = path_to_check
            bb.note(f"Found base DTS at: {base_dts_path}")
            break
    
    if not base_dts_path:
        bb.fatal(f"Could not find the base device tree source file '{base_dts}' in any of the DT_INCLUDE paths.")

    # Compile the base DTS to DTB
    devicetree_compile(base_dts_path, includes, d)

    # Find all compiled overlays (.dtbo)
    overlays = glob.glob(os.path.join(b_dir, "*.dtbo"))
    if not overlays:
        bb.warn(f"BASE_DTS was specified, but no overlay .dtbo files were found to merge. The merged file '{merged_dtb_name}' will be a copy of the base DTB.")
        import shutil
        shutil.copyfile(base_dtb, merged_dtb_path)
        return

    # Run fdtoverlay to merge them all
    bb.note(f"Merging {len(overlays)} overlay(s) into '{base}.dtb'...")
    fdtoverlay_args = ["fdtoverlay", "-o", merged_dtb_path, "-i", base_dtb] + overlays
    bb.note(f"Running {' '.join(fdtoverlay_args)}")
    subprocess.run(fdtoverlay_args, check = True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)

devicetree_do_compile:append(){
    devicetree_merge_overlays(d)
}



