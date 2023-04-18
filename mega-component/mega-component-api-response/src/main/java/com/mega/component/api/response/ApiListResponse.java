package com.mega.component.api.response;

import java.util.List;

/**
 * List<?> l = new ArrayList<>();
 * eg1:
 *  ApiListResponse.of(new ApiList<>(l));
 * eg2:
 *  ApiListResponse.of(l);
 * @param <U>
 */
public class ApiListResponse<U> extends ApiSuccessResponse<ApiListResponse.ApiList<U>> {

    public ApiListResponse(ApiList<U> data) {
        super(data);
    }

    public static <U> ApiResponseInterface<ApiList<U>> of(ApiList<U> data) {
        return new ApiSuccessResponse<>(data);
    }

    public static <U> ApiResponseInterface<ApiList<U>> of(List<U> data) {
        return new ApiSuccessResponse<>(new ApiList<>(data));
    }

    public static class ApiList<T> {
        private final List<T> items;

        public ApiList(List<T> items) {
            this.items = items;
        }

        public List<T> getItems() {
            return items;
        }
    }

}
