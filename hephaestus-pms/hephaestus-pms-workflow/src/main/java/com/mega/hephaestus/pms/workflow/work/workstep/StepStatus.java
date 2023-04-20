package com.mega.hephaestus.pms.workflow.work.workstep;


import com.mega.component.nuc.message.EnumConst;

public enum StepStatus {

    NonStarted(0, "未开始"),
    Started(1, "运行中"),
    Completed(2, "已完成"),
    ;

    private final int code;

    private final String name;

    StepStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public static StepStatus toEnum(final int code) {
        for (StepStatus versionEnum : StepStatus.values()) {
            if (versionEnum.getCode() == code) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, code));
    }
}
