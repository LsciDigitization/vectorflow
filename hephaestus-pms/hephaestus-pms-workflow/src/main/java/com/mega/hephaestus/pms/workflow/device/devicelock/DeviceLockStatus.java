package com.mega.hephaestus.pms.workflow.device.devicelock;


import com.mega.component.nuc.message.EnumConst;

/**
 * @author 胡贤明
 * 设备锁状态
 */
public enum DeviceLockStatus {
    Running("running", "运行中"),
    Await("await","等待")
    ;


    private final String code;

    private final String value;

    DeviceLockStatus(String code, String value) {
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

    public static DeviceLockStatus toEnum(final String code) {
        for (DeviceLockStatus versionEnum : DeviceLockStatus.values()) {
            if (versionEnum.getCode().equals(code)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, code));
    }

}
