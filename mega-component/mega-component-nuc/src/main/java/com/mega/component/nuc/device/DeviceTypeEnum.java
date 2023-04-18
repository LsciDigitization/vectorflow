package com.mega.component.nuc.device;

import com.mega.component.nuc.message.EnumConst;

public enum DeviceTypeEnum implements DeviceType {
    System("System", "系统"),
    Unknown("Unknown", "未知"),
    None("None", "None"),

    Workstation("Workstation", "移液工作站"),
    Incubator("Incubator", "培养箱"),
    Washer("Washer", "洗板机"),
    Reader("Reader", "酶标仪"),
    Centrifuge("Centrifuge", "离心机"),
    Cytometer("Cytometer", "细胞仪"),
    HCS("HCS", "高内涵"),
    Multidrop("Multidrop", "分液器"),
    LabwareHandler("LabwareHandler", "机械臂"),
    LabwareHandlerMZ04("LabwareHandlerMZ04", "机械臂MZ04"),
    LabwareHandlerPF400("LabwareHandlerPF400", "机械臂PF400"),
    IntelliXcap("IntelliXcap", "开关盖机"),
    RegripStation("RegripStation", "交换站"),
    Carousel("Carousel", "旋转板站"),
    LidHandler("LidHandler", "吸盖器"),
    Scanner("Scanner", "扫码器"),
    LabelPrinter("LabelPrinter", "条码打印机"),
    ViaFill("ViaFill", "微量加样仪"),
    Star("Star", "液体工作站"),
    Sealer("Sealer", "封膜机"),
    LPX440("LPX440", "旋转板站"),
    STX220("STX220", "4度控湿冰箱"),
    ShakePool("ShakePool", "震荡器"),
    XPeel("XPeel", "撕膜机"),
    PCR("PCR", "PCR仪"),
    SPEA200("SPEA200", "固相萃取仪"),

    ;

    private final String code;
    private final String label;

    DeviceTypeEnum(String code, String label) {
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
        for (DeviceTypeEnum versionEnum: DeviceTypeEnum.values()) {
            if (versionEnum.getCode().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
