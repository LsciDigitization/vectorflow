package com.mega.hephaestus.pms.workflow.work.worktask;

public enum MessageTopicEnum {

    Default("Default", "默认"),
    ;

    private final String code;

    private final String name;

    MessageTopicEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return code;
    }


}
