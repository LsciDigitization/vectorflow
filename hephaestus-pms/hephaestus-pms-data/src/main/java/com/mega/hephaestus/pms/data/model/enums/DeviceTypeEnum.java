package com.mega.hephaestus.pms.data.model.enums;

/**
 * @author 胡贤明
 * 存储状态
 */
public enum DeviceTypeEnum {
    System("System", "系统"),
    Unknown("Unknown", "未知"),
    None("None", "None"),

    LabwareHandlerMZ04("LabwareHandlerMZ04", "机械臂MZ04"),
    LabwareHandlerPF400("LabwareHandlerPF400", "机械臂PF400"),

    Workstation("Workstation", "移液工作站"),
    Incubator("Incubator", "培养箱"),
    Washer("Washer", "洗板机"),
    Reader("Reader", "酶标仪"),
    Centrifuge("Centrifuge", "离心机"),
    RegripStation("RegripStation", "交换站"),
    Carousel("Carousel", "旋转板站"),
    LidHandler("LidHandler", "吸盖器"),
    Scanner("Scanner", "扫码器"),
    HCS("HCS", "高内涵"),
    Multidrop("Multidrop", "分液器"),
    LabelPrinter("LabelPrinter", "条码打印机"),
    Cytometer("Cytometer", "细胞仪"),

    // DMPK
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

    private final String name;

    private final String value;

    DeviceTypeEnum(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static DeviceTypeEnum toEnum(final String value) {
        for (DeviceTypeEnum versionEnum : DeviceTypeEnum.values()) {
            if (versionEnum.getValue().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    public static DeviceTypeEnum toEnumByName(final String name){
        for (DeviceTypeEnum versionEnum : DeviceTypeEnum.values()) {
            if (versionEnum.getName().equals(name)) {
                return versionEnum;
            }
        }
        return Unknown  ;
    }
}
