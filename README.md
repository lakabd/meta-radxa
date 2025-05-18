# Yocto layer for RADXA Rock-5 Model A

## Supported boards:
- Radxa Rock-5 Model A

## Dependencies:
- **poky:**
    ```
    URI: https://git.yoctoproject.org/git/poky
    layers: meta, meta-poky, meta-yocto-bsp:
    branch: scarthgap
    ```
- **openembedded:**
    ```
    URI: http://git.openembedded.org/meta-openembedded
    layers: meta-oe, meta-networking, meta-python and meta-perl
    branch: scarthgap
    ```
- **rockchip:**
    ```
    URI: https://git.yoctoproject.org/meta-rockchip
    layers: .
    branch: scarthgap
    ```
- **arm:**
    ```
    URI: git://git.yoctoproject.org/meta-arm
    layers: meta-arm, meta-arm-toolchain
    branch: scarthgap
    ```

## How to?
To make the build easier, [kas](https://github.com/siemens/kas) can be used.

```
sudo apt update
sudp apt install kas
```

Under scripts/ folder, a KAS yaml file is provided for setting up everything and starting the build.

Usage:
```
kas-container shell kas-rock.yml
```
Then:
```
bitbake core-image-minimal
```

