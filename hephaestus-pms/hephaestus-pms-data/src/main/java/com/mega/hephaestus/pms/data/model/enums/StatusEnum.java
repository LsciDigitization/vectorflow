package com.mega.hephaestus.pms.data.model.enums;

/**
 * @author 胡贤明
 * 状态
 */
public enum StatusEnum {
    DISABLE("disable", "禁用"),
    NORMAL("normal", "正常"),
    LOCK("lock", "锁定"),



    ;

    private final String name;

    private final String value;

    StatusEnum(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static StatusEnum toEnum(final String value) {
        for (StatusEnum versionEnum : StatusEnum.values()) {
            if (versionEnum.getValue() .equals( value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }
}
