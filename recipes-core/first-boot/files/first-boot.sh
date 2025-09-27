#!/bin/bash

MMC_DEV="/dev/mmcblk0"
ROOTFS_SIZE=(6 12582912) # (GiB Sectors)
ROOTFS_DEV=$(blkid -L rootfs)
USER_DEV=$(blkid -L user)
USER_PART=3
USER_UUID="3797a48f-aae6-48ec-83e8-f7bdf9c85c25"

set -e

resize_part()
{
    local DEV=$1
    local PART=${DEV##*p}
    local SZ_GiB=$2

    # Fix GPT backup header.
    # WIC places it at the end of the image, and we needs it to be at the end of disk.
    sgdisk -e $MMC_DEV
    partprobe $MMC_DEV

    # Resize
    parted -s $MMC_DEV resizepart $PART ${SZ_GiB}GiB
    partprobe $MMC_DEV

    # Extend fs
    resize2fs $DEV
    partprobe $MMC_DEV

    echo "[+] Successfully resized rootfs to ${SZ_GiB}GiB."
}

create_part()
{
    local NAME=$1
    local PART=$2
    local UUID=$3

    # Largest free block
    sgdisk -N $PART $MMC_DEV
    partprobe $MMC_DEV

    # Set new part name and type
    sgdisk -c $PART:$NAME -t $PART:8300 $MMC_DEV
    partprobe $MMC_DEV

    # Format new part
    mkfs.ext4 -L $NAME -U $UUID ${MMC_DEV}p${PART}
    partprobe $MMC_DEV

    echo "[+] Successfully created $NAME partition."
}

# Check if rootfs needs to be resized
if [ -n "$ROOTFS_DEV" ]; then
    SZ_Sectors=$(blockdev --getsize $ROOTFS_DEV)
    if [ $SZ_Sectors -le ${ROOTFS_SIZE[1]} ]; then
        resize_part $ROOTFS_DEV ${ROOTFS_SIZE[0]}
    fi
else
    exit 1
fi

# Check if user part exists, if not create it
if [ -z "$USER_DEV" ]; then
    create_part "user" $USER_PART $USER_UUID
fi

# Set flag
touch /etc/first-boot.done

exit 0