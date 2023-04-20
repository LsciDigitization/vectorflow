package com.mega.hephaestus.pms.workflow.general.config;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.message.EnumConst;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/31 22:02
 */
public enum GeneralDeviceTypeEnum implements DeviceType {

    Unknown("Unknown", "未知"),

    // 以下为DMPK设备类型
    Centrifuge("Centrifuge", "离心机"),
    ViaFill("ViaFill", "微量加样仪"),
    Star("Star", "液体工作站"),
    Sealer("Sealer", "封膜机"),
    LPX440("LPX440", "旋转板站"),
    IntelliXcap("IntelliXcap", "开关盖机"),
    STX220("STX220", "4度控湿冰箱"),
    ShakePool("ShakePool", "震荡器"),
    XPeel("XPeel", "撕膜机"),
    PCR("PCR", "PCR仪"),
    SPEA200("SPEA200", "固相萃取仪"),
    LabwareHandler("LabwareHandler", "机械臂"),

    ;

    private final String code;
    private final String label;

    GeneralDeviceTypeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return code;
    }

    public static DeviceType toEnum(final String value) {
        for (DeviceType type: values()) {
            if (type.getCode().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
