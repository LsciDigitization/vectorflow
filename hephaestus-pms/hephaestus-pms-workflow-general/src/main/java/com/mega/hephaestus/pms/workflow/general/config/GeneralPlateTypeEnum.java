package com.mega.hephaestus.pms.workflow.general.config;

import com.mega.component.nuc.message.EnumConst;
import com.mega.component.nuc.plate.PlateType;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/31 22:00
 */
public enum GeneralPlateTypeEnum implements PlateType {

    // dmpk
    SAMPLE("sample","样品"),
    STANDARD("standard","标品"),
    EMPTY("empty","空板"),
    PIPETTE1("pipette1","枪头1"),
    PIPETTE2("pipette2","枪头2"),
    ;

    private final String code;

    private final String label;

    GeneralPlateTypeEnum(String code, String label) {
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
        for (PlateType type : values()) {
            if (type.getCode().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
