package com.mega.hephaestus.pms.data.model.enums;

/**
 * 实验阶段状态枚举
 */
public enum ExperimentGroupStatusEnum {
    PREDEFINED(0,"预定义"),
    INIT(5,"初始化"),
    RUNNING(10,"运行"),
    FINISHED(15,"完成"),
//    RECOVER(20,"板位已复位"),
    Stop(20,"中断");
    private final String name;

    private final int value;
    ExperimentGroupStatusEnum(int value, String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static ExperimentGroupStatusEnum toEnum(final int value) {
        for (ExperimentGroupStatusEnum versionEnum : ExperimentGroupStatusEnum.values()) {
            if (versionEnum.getValue() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }
}
