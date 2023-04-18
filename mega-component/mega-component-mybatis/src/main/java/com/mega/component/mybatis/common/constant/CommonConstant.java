package com.mega.component.mybatis.common.constant;

/**
 * @author 胡贤明
 */
public enum  CommonConstant {
    STATUS_DISABLE(0),
    STATUS_NORMAL(1),
    STATUS_LOCK(2),
    STATUS_DELETE(3)

    ;

    private final Integer code;

    CommonConstant(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


    @Override
    public String toString() {
        return code.toString();
    }
}
