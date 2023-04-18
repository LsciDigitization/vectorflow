package com.mega.component.nuc.device;

import java.util.Objects;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/29 17:58
 */
public class GenericDeviceType implements DeviceType {

    private final String code;
    private final String label;

    public GenericDeviceType(String code) {
        this(code, code);
    }

    public GenericDeviceType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String name() {
        return code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericDeviceType that = (GenericDeviceType) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
