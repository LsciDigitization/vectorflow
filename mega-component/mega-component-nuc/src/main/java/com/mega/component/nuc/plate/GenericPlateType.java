package com.mega.component.nuc.plate;

import java.util.Objects;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/29 18:07
 */
public class GenericPlateType implements PlateType {
    private final String code;
    private final String label;

    public GenericPlateType(String code) {
        this(code, code);
    }

    public GenericPlateType(String code, String label) {
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
        GenericPlateType that = (GenericPlateType) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public static PlateType toEnum(final String value) {
        return new GenericPlateType(value, value);
    }

    public static PlateType toEnum(final String value, final String label) {
        return new GenericPlateType(value, label);
    }
}
