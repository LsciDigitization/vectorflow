package com.mega.component.api.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 通用响应信息类
 */
public class ApiResult<T> extends ApiSuccessResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = HttpStatus.OK.value();

    /**
     * 失败
     */
    public static final int FAIL = HttpStatus.INTERNAL_SERVER_ERROR.value();

    protected int code;

    protected String message;

    protected T data;

    public ApiResult(T data, int code, String msg) {
        super(code, msg, data);

        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ApiResult(int code, String msg) {
        super(code, msg, null);

        this.code = code;
        this.message = msg;
    }

    @SuppressWarnings("unused")
    public ApiResult() {
        super(-1, null, null);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(null, SUCCESS, null);
    }

    public static <T> ApiResult<T> success(String msg) {
        return new ApiResult<>(null, SUCCESS, msg);
    }

    public static <T> ApiResult<T> data(T data) {
        return new ApiResult<>(data, SUCCESS, null);
    }

    public static <T> ApiResult<T> data(T data, String msg) {
        return new ApiResult<>(data, SUCCESS, msg);
    }


    public static <T> ApiResult<T> fail(String msg) {
        return new ApiResult<>(FAIL, msg);
    }

    @SuppressWarnings("unused")
    public static <T> ApiResult<T> fail(T data) {
        return new ApiResult<>(data, FAIL, null);
    }

    @SuppressWarnings("unused")
    public static <T> ApiResult<T> fail(T data, String msg) {
        return new ApiResult<>(data, FAIL, msg);
    }

    @SuppressWarnings("unused")
    public static <T> ApiResult<T> fail(int code, String msg) {
        return new ApiResult<>(null, code, msg);
    }

    public static <T> ApiResult<T> status(boolean flag) {
        return flag ? success(Constants.DEFAULT_SUCCESS_MESSAGE) : fail(Constants.DEFAULT_FAILURE_MESSAGE);
    }

    public static <T> ApiResult<T> status(int rows) {
        return rows > 0 ? success(Constants.DEFAULT_SUCCESS_MESSAGE) : fail(Constants.DEFAULT_FAILURE_MESSAGE);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
