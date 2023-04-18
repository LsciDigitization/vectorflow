package com.mega.component.api.response;

import java.util.List;

/**
 * List<?> l = new ArrayList<>();
 * eg1:
 *  ApiListResponse.of(new ApiPageList<>(l, 1, 10));
 * eg2:
 *  ApiListResponse.of(l, 1, 10);
 * @param <U>
 */
public class ApiPageListResponse<U> extends ApiSuccessResponse<ApiPageListResponse.ApiPageList<U>> {

    public ApiPageListResponse(ApiPageList<U> data) {
        super(data);
    }

    public static <U> ApiResponseInterface<ApiPageList<U>> of(ApiPageList<U> data) {
        return new ApiSuccessResponse<>(data);
    }

    public static <U> ApiResponseInterface<ApiPageList<U>> of(List<U> data, int currentPage, int total, int perPage) {
        return new ApiSuccessResponse<>(new ApiPageList<>(data, currentPage, total, perPage));
    }

    public static <U> ApiResponseInterface<ApiPageList<U>> of(List<U> data, int currentPage, int total) {
        return new ApiSuccessResponse<>(new ApiPageList<>(data, currentPage, total));
    }

    public static <U> ApiResponseInterface<ApiPageList<U>> of(List<U> data, ApiPageMeta meta) {
        return new ApiSuccessResponse<>(new ApiPageList<>(data, meta));
    }

    public static class ApiPageList<T> {

        private final List<T> items;

        private final ApiPageMeta meta;

        public ApiPageList(List<T> items, ApiPageMeta meta) {
            this.items = items;
            this.meta = meta;
        }

        public ApiPageList(List<T> items, int currentPage, int total, int perPage) {
            this.items = items;
            this.meta = new ApiPageMeta(currentPage, perPage, total);
        }

        public ApiPageList(List<T> items, int currentPage, int total) {
            this.items = items;
            this.meta = new ApiPageMeta(currentPage, total, 20);
        }

        public List<T> getItems() {
            return items;
        }

        public ApiPageMeta getMeta() {
            return meta;
        }
    }

    public static class ApiPageMeta {
        private final int currentPage; // 当前页
        private final int perPage; // 每页条数
        private final int lastPage; // 最后一页
        private final int total; // 总条数

        private final String nextPageUrl; // 下一页地址

        private final String pervPageUrl; // 上一页地址

        public ApiPageMeta(int currentPage, int total, int perPage) {
            this.currentPage = currentPage;
            this.total = total;
            this.perPage = perPage;
            this.lastPage = total / perPage + 1;
            this.nextPageUrl = null;
            this.pervPageUrl = null;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        @SuppressWarnings("unused")
        public int getPerPage() {
            return perPage;
        }

        @SuppressWarnings("unused")
        public int getLastPage() {
            return lastPage;
        }

        public int getTotal() {
            return total;
        }

        @SuppressWarnings("unused")
        public String getNextPageUrl() {
            return nextPageUrl;
        }

        @SuppressWarnings("unused")
        public String getPervPageUrl() {
            return pervPageUrl;
        }
    }

}
