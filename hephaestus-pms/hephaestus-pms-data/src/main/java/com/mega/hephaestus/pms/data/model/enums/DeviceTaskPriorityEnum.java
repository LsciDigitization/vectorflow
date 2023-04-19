package com.mega.hephaestus.pms.data.model.enums;


/**
 * 实验阶段状态枚举
 */
public enum DeviceTaskPriorityEnum {
    High(1,"高"),
    Medium(11,"中"),
    Low(111,"低"),


    ;

    private final String name;
    private final int value;
    DeviceTaskPriorityEnum(int value, String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static DeviceTaskPriorityEnum toEnum(final int value) {
        for (DeviceTaskPriorityEnum versionEnum : DeviceTaskPriorityEnum.values()) {
            if (versionEnum.getValue() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }
}
