package com.mega.hephaestus.pms.data.model.enums;

/**
 * @author 胡贤明
 * 存储状态
 */
public enum StorageModelStatusEnum {
    FREE(1,"空闲"),
    OCCUPY(2,"占用"),
    UNKNOWN(0,"未知"),

    ;

    private final String name;

    private final int value;

    StorageModelStatusEnum(int value, String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static StorageModelStatusEnum toEnum(final int value) {
        for (StorageModelStatusEnum versionEnum : StorageModelStatusEnum.values()) {
            if (versionEnum.getValue() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
