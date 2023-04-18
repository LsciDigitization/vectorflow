package com.mega.component.nuc.plate;

import com.mega.component.nuc.message.EnumConst;

/**
 * 板枚举
 */
public enum PlateTypeEnum implements PlateType {
    // dmpk
    SAMPLE("sample","样品"),
    STANDARD("standard","标品"),
    EMPTY("empty","空板"),
    PIPETTE1("pipette1","枪头1"),
    PIPETTE2("pipette2","枪头2"),

    ;

    private final String code;

    private final String label;

    PlateTypeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return code;
    }

    public static PlateType toEnum(final String value) {
        for (PlateTypeEnum versionEnum : PlateTypeEnum.values()) {
            if (versionEnum.getCode() .equals( value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }
}
