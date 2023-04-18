package com.mega.component.mybatis.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author 删除状态
 */
public enum BooleanEnum {
    NO(0, "否"),
    YES(1, "是"),

    ;

    static final String NO_MATCHING_ENUMS_ERROR_MESSAGE = "No matching enum found for %s";

    @EnumValue
    private final Integer code;

    private final String description;

    BooleanEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static BooleanEnum toEnum(final int value) {
        for (BooleanEnum versionEnum : BooleanEnum.values()) {
            if (versionEnum.getCode() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    public boolean toBoolean() {
        return code == 1;
    }

    @Override
    public String toString() {
        return code.toString();
    }
}
