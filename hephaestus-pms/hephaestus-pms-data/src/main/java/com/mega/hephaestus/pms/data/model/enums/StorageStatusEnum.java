package com.mega.hephaestus.pms.data.model.enums;

import java.util.Objects;

/**
 * @author 胡贤明
 * 存储状态
 */
public enum StorageStatusEnum {
    IDLE("idle", "空闲"),
    BUSY("busy", "占用"),
    LOCK("lock", "锁定")

    ;

    private final String name;

    private final String value;

    StorageStatusEnum(String value, String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static StorageStatusEnum toEnum(final String value) {
        for (StorageStatusEnum versionEnum : StorageStatusEnum.values()) {
            if (Objects.equals(versionEnum.getValue(), value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
