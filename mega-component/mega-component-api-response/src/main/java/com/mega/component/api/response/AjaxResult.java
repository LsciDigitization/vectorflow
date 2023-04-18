package com.mega.component.api.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作消息提醒
 */
public class AjaxResult extends ApiSuccessResponse<Map<String, Object>> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据对象
     */
    public static final String DEFAULT_DATA_TAG = "data";

    /**
     * 状态码
     */
    protected String code;

    /**
     * 返回内容
     */
    protected String message;

    protected Map<String, Object> data = new HashMap<>();

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
        super(-1, null, null);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super(code, msg, null);

        this.code = String.valueOf(code);
        this.message = msg;
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Map<String, Object> data) {
        super(code, msg, null);

        this.code = String.valueOf(code);
        this.message = msg;
        this.data.putAll(data);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super(code, msg, null);

        this.code = String.valueOf(code);
        this.message = msg;
        this.data.put(DEFAULT_DATA_TAG, data);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(String code, String msg, Map<String, Object> data) {
        super(-1, msg, null);

        this.code = code;
        this.message = msg;
        this.data.putAll(data);
    }

    public AjaxResult(String code, String msg, Object data) {
        super(-1, msg, null);

        this.code = code;
        this.message = msg;
        this.data.put(DEFAULT_DATA_TAG, data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 失败消息
     */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, Map.of());
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */
    public AjaxResult put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 移除data数据
     * @return 返回数据对象
     */
    @SuppressWarnings("unused")
    public AjaxResult removeData() {
        this.data = null;
        return this;
    }
}
