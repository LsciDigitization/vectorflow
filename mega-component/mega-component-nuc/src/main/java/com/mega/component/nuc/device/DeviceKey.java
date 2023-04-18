package com.mega.component.nuc.device;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 11:44
 */
public class DeviceKey {

    private final String name;

    public DeviceKey(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static DeviceKey toEnum(final String value) {
        return new DeviceKey(value);
    }

}
