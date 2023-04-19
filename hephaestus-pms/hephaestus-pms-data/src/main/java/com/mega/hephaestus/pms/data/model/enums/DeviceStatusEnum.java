package com.mega.hephaestus.pms.data.model.enums;


/**
 * @author 胡贤明
 * 存储状态
 */
public enum DeviceStatusEnum {
    STOP("stop", "停止"),
    RESETTING("resetting", "初始化中"),
    IDLE("idle", "就绪"),
    BUSY("busy", "运行中"),
    INERROR("inerror", "错误"),
    INWARNING("inwarning", "警告"),



    ;

    private final String name;

    private final String value;

    DeviceStatusEnum(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static DeviceStatusEnum toEnum(final String value) {
        for (DeviceStatusEnum versionEnum : DeviceStatusEnum.values()) {
            if (versionEnum.getValue() .equals( value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }
}
