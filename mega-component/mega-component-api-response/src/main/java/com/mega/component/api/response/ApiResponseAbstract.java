package com.mega.component.api.response;

abstract class ApiResponseAbstract {

    protected final int code;

    protected final String message;

    public ApiResponseAbstract(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ApiResponseAbstract{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
