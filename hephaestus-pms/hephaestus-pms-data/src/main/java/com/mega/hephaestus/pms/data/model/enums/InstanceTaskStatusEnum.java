package com.mega.hephaestus.pms.data.model.enums;


/**
 * @author 胡贤明
 *  设备task 状态枚举
 */
public enum InstanceTaskStatusEnum {
    Await("await", "等待"),
    Running("running", "进行"),
    Finished("finished", "完成"),
    Failed("failed","失败")

    ;

    private final String name;

    private final String value;

    InstanceTaskStatusEnum(String value, String name) {
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

    public static InstanceTaskStatusEnum toEnum(final String value) {
        for (InstanceTaskStatusEnum versionEnum : InstanceTaskStatusEnum.values()) {
            if (versionEnum.getValue().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
