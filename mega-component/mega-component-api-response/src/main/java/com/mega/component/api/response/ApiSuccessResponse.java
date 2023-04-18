package com.mega.component.api.response;

public class ApiSuccessResponse<T> extends ApiResponseAbstract implements ApiResponseInterface<T> {

    protected final T data;

    public ApiSuccessResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public ApiSuccessResponse(T data) {
        super(200, "Success");
        this.data = data;
    }

    public static <T> ApiResponseInterface<T> of() {
        return new ApiSuccessResponse<>(null);
    }

    public static <T> ApiResponseInterface<T> of(T data) {
        return new ApiSuccessResponse<>(data);
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ApiSuccessResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                "} ";
    }

}
