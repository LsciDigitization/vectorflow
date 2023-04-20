package com.mega.hephaestus.pms.workflow.manager;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/13 18:11
 */
public enum SampleTransferMethod {
    PARTIAL("partial"),
    WHOLE("whole"),
    MIX("mix");

    private final String code;

    SampleTransferMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

}
