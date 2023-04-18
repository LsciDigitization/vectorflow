package com.mega.component.nuc.device;

import com.mega.component.nuc.message.EnumConst;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/21 17:22
 */
public enum DeviceFamilyEnum implements DeviceFamily {

    System("System", "系统"),
    Unknown("Unknown", "未知"),
    None("None", "None"),

    LabwareHandler("LabwareHandler", "机械臂4轴"),
    Incubator("Incubator", "培养保温箱"),
    Carousel("Carousel", "旋转板栈"),
    Multidrop("Multidrop", "分液器"),
    WorkStation("WorkStation", "移液工作站"),
    Washer("Washer", "洗板机"),
    Reader("Reader", "酶标仪"),
    Centrifuge("Centrifuge", "离心机"),
    RegripStation("RegripStation", "交换站"),
    LidHandler("LidHandler", "吸盖器"),
    Scanner("Scanner", "扫码器"),
    HCS("HCS", "高内涵"),
    LabelPrinter("LabelPrinter", "条码打印机"),
    Cytometer("Cytometer", "细胞仪"),
    ViaFill("ViaFill", "微量加样仪"),
    Sealer("Sealer", "封膜机"),
    ShakePool("ShakePool", "震荡器"),
    PCR("PCR", "PCR仪"),
    XPeel("XPeel", "撕膜机"),
    XCap("XCap", "开盖机"),
    ;


    private final String code;
    private final String label;

    DeviceFamilyEnum(String code, String label) {
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

    public static DeviceFamily toEnum(final String value) {
        for (DeviceFamilyEnum versionEnum: DeviceFamilyEnum.values()) {
            if (versionEnum.getCode().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
