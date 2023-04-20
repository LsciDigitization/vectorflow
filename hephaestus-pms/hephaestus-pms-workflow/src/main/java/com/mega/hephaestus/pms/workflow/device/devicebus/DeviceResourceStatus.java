package com.mega.hephaestus.pms.workflow.device.devicebus;


import com.mega.component.nuc.message.EnumConst;

public enum DeviceResourceStatus {

    // 空闲
    IDLE("idle", "空闲"),

    // 运行
    RUNNING("running", "运行"),

    ;


    private final String code;

    private final String value;

    DeviceResourceStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return code;
    }

    public static DeviceResourceStatus toEnum(final String code) {
        for (DeviceResourceStatus versionEnum : DeviceResourceStatus.values()) {
            if (versionEnum.getCode().equals(code)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, code));
    }

}
