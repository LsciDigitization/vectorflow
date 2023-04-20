package com.mega.hephaestus.pms.workflow.task.tasklock;


import com.mega.component.nuc.message.EnumConst;

public enum TaskLockStatus {
    DEFAULT(0), // 默认
    ENTRY_LOCK(1), // 前置锁
    NOW_LOCK(2), // 即时锁
    ;

    private final int code;

    TaskLockStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public static TaskLockStatus toEnum(final int value) {
        for (TaskLockStatus versionEnum: TaskLockStatus.values()) {
            if (versionEnum.getCode() == value) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
