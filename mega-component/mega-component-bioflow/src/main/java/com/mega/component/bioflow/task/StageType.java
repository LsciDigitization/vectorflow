package com.mega.component.bioflow.task;

import com.mega.component.nuc.message.EnumConst;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/7 15:40
 */
public enum StageType {
    Start("start", "开始"),
    Dynamic("dynamic", "执行"),
    Stage("stage", "执行"),
    Await("await", "等待"),
    Gateway("gateway", "判断"),
    Condition("condition", "条件"),
    End("end", "结束");

    public final String name;

    public final String value;

    StageType(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static StageType toEnum(final String value) {
        for (StageType versionEnum : StageType.values()) {
            if (versionEnum.getValue().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

    @Override
    public String toString() {
        return value;
    }

}
