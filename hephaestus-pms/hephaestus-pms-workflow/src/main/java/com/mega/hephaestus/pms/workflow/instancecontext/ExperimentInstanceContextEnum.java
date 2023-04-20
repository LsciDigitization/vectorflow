package com.mega.hephaestus.pms.workflow.instancecontext;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.mega.component.nuc.message.EnumConst;

/**
 * 实验状态枚举
 */
@Deprecated(since = "20221115")
public enum ExperimentInstanceContextEnum {
    INCUBATOR("INCUBATOR", "二氧化碳培育箱");

    public final String name;
    @EnumValue
    public final String value;

    ExperimentInstanceContextEnum(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static ExperimentInstanceContextEnum toEnum(final String value) {
        for (ExperimentInstanceContextEnum versionEnum : ExperimentInstanceContextEnum.values()) {
            if (versionEnum.getValue().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }
}
