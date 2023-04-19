package com.mega.hephaestus.pms.data.model.enums;


/**
 * @author 胡贤明
 *  设备task 状态枚举
 */
public enum DeviceTaskStatusEnum {
    Running("running", "进行"),
    Finished("finished", "完成"),
    Failed("failed","失败")

    ;

    private final String name;

    private final String value;

    DeviceTaskStatusEnum(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static DeviceTaskStatusEnum toEnum(final String value) {
        for (DeviceTaskStatusEnum versionEnum : DeviceTaskStatusEnum.values()) {
            if (versionEnum.getValue().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
