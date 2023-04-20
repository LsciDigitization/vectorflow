package com.mega.hephaestus.pms.workflow.manager;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/13 20:06
 */
public enum TransferType {
    SAMPLE("sample"),
    LIQUID("liquid");

    private final String code;

    TransferType(String code) {
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
