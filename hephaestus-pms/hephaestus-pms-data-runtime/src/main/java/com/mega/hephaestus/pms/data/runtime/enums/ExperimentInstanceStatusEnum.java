package com.mega.hephaestus.pms.data.runtime.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mega.hephaestus.pms.data.model.enums.EnumConst;

/**
 * 实验状态枚举
 */
public enum ExperimentInstanceStatusEnum {
    IDLE(0,"就绪"),
    RUNNING(5,"执行"),
    SUSPEND(10,"中断"),
    FAIL(15,"失败"),
    FINISHED(20,"完成");

    private final String name;
    @EnumValue
    private final int value;
    ExperimentInstanceStatusEnum(int value,String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static ExperimentInstanceStatusEnum toEnum(final int value) {
        for (ExperimentInstanceStatusEnum versionEnum : ExperimentInstanceStatusEnum.values()) {
            if (versionEnum.getValue() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }
}
