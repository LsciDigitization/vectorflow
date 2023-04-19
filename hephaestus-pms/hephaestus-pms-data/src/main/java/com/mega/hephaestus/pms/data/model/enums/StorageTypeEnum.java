package com.mega.hephaestus.pms.data.model.enums;

/**
 * @author 胡贤明
 * 存储状态
 */
@Deprecated(since = "20221115")
public enum StorageTypeEnum {
    TYPE_384(1,"384孔板"),
    TYPE_96(2,"96孔板"),
    TYPE_OTHER(0,"其他"),

    ;

    private final String name;

    private final int value;

    StorageTypeEnum(int value, String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static StorageTypeEnum toEnum(final int value) {
        for (StorageTypeEnum versionEnum : StorageTypeEnum.values()) {
            if (versionEnum.getValue() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
