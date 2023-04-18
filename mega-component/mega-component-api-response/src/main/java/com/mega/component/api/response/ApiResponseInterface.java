package com.mega.component.api.response;

public interface ApiResponseInterface<T> {

    int getCode();

    String getMessage();

    T getData();

}
