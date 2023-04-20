package com.mega.hephaestus.pms.workflow.work.worktask;

public enum MessageStatusEnum {

    Default(0, "默认"),
    ;

    private final int code;

    private final String name;

    MessageStatusEnum(int code, String name) {
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

}
