package com.mega.hephaestus.pms.data.model.enums;

/**
 * @author 胡贤明
 */
public enum XcapStatusEnum {

    None(0,"未开"),
    Open(1,"已开"),
    Close(2,"已关"),

    ;

    private final String name;

    private final int value;

    XcapStatusEnum(int value, String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static XcapStatusEnum toEnum(final int value) {
        for (XcapStatusEnum versionEnum : XcapStatusEnum.values()) {
            if (versionEnum.getValue() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
