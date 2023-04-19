package com.mega.hephaestus.pms.data.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author 胡贤明
 * 设备模型状态
 */
public enum DeviceModelTypeEnum {
    UNKNOWN("unknown", "未知"),
    OPERATION("operation", "操作类设备"),
    STORAGE("storage", "存储类设备"),
    SCHEDULING("scheduling", "调度类设备"),





    ;

    private final String name;

    @EnumValue
    private final String value;

    DeviceModelTypeEnum(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据value值获取枚举
     * @param value 值
     * @return 枚举
     */
    public static DeviceModelTypeEnum toEnum(final String value) {
        for (DeviceModelTypeEnum versionEnum : DeviceModelTypeEnum.values()) {
            if (versionEnum.getValue().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    /**
     * 根据名称获取枚举
     * @param name 名称
     * @return 枚举
     */
    public static DeviceModelTypeEnum toEnumByName(final String name){
        for (DeviceModelTypeEnum deviceModelTypeEnum : DeviceModelTypeEnum.values()) {
            if (deviceModelTypeEnum.getName().equals(name)) {
                return deviceModelTypeEnum;
            }
        }
        return UNKNOWN;

    }
}
