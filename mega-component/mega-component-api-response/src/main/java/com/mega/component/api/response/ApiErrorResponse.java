package com.mega.component.api.response;

public class ApiErrorResponse extends ApiResponseAbstract implements ApiResponseInterface<Object> {

    public ApiErrorResponse(int code, String message) {
        super(code, message);
    }

    public static ApiErrorResponse of(int code, String msg) {
        return new ApiErrorResponse(code, msg);
    }

    @Override
    public String toString() {
        return "ApiSuccessResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                "} ";
    }

    @Override
    public Object getData() {
        return null;
    }

}
