package com.mega.hephaestus.pms.data.model.enums;

/**
 * @author 胡贤明
 * 设备分组
 */
public enum DeviceTypeFamilyEnum {
    Unknown("Unknown", "未知"),
    LabwareHandler("LabwareHandler", "机械臂"),


    ;

    private final String name;

    private final String value;

    DeviceTypeFamilyEnum(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static DeviceTypeFamilyEnum toEnum(final String value) {
        for (DeviceTypeFamilyEnum versionEnum : DeviceTypeFamilyEnum.values()) {
            if (versionEnum.getValue() .equals( value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    public static DeviceTypeFamilyEnum toEnumByName(final String name){
        for (DeviceTypeFamilyEnum deviceTypeFamilyEnum : DeviceTypeFamilyEnum.values()) {
            if (deviceTypeFamilyEnum.getName().equals(name)) {
                return deviceTypeFamilyEnum;
            }
        }
        return Unknown;
    }
}
