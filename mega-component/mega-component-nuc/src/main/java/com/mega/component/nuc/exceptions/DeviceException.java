package com.mega.component.nuc.exceptions;

import lombok.Getter;

@Getter
public class DeviceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private final String module;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误码对应的参数
     */
    private final Object[] args;

    /**
     * 错误消息
     */
    private final String message;

    public DeviceException(String module, String code, String message, Object... args) {
        this.module = module;
        this.code = code;
        this.message = message;
        this.args = args;
    }

    public DeviceException(String module, String code, Object... args) {
        this(module, code, null, args);
    }

    public DeviceException(String module, String message) {
        this(module, null, message, (Object) null);
    }

    public DeviceException(String code, Object... args) {
        this(null, code, null, args);
    }


    public DeviceException(String message) {
        this(null, null, message, (Object) null);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
