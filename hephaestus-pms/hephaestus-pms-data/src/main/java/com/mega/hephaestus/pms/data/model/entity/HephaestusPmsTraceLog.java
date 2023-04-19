package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * PMS操作日志
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_pms_trace_log")
public class HephaestusPmsTraceLog {

    /**
     * 自增id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 一次任务分配一个唯一TraceId
     */
    private String traceId;


    /**
     * 一个命令一个唯一SpanId
     */
    private String spanId;


    /**
     * 上一阶段的命令的SpanId
     */
    private String parentSpanId;


    /**
     * 设备id
     */
    private String deviceId;


    /**
     * 设备类型
     */
    private String deviceType;


    /**
     * 请求id
     */
    private String requestId;


    /**
     * 事件id
     */
    private String eventId;


    /**
     *
     */
    private String eventDeviceId;


    /**
     * 命令
     */
    private String command;


    /**
     * 请求内容
     */
    private String requestContent;


    /**
     * 请求时间
     */
    private Date requestTime;


    /**
     * 响应内容
     */
    private String responseContent;


    /**
     * 响应时间
     */
    private Date responseTime;


    /**
     * 回调内容
     */
    private String callbackContent;


    /**
     * 回调时间
     */
    private Date callbackTime;


    /**
     * 是否回调
     */
    private Integer isCallback;


    /**
     * 回调类型 onResponse、onWarning、onError
     */
    private String callbackType;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;


    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否删除 0 否 1是
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private BooleanEnum isDeleted;

    /**
     * 删除人
     */
    private String deleteBy;
    /**
     * 删除时间
     */
    private Date deleteTime;


    @Data
    @Builder
    public static class PmsTraceLogBuilder {
        private final HephaestusPmsTraceLog traceLog = new HephaestusPmsTraceLog();

        public static PmsTraceLogBuilder create() {
            return new PmsTraceLogBuilder();
        }

        public PmsTraceLogBuilder traceBase(String deviceId, String deviceType, String traceId, String requestId) {
            this.traceLog.deviceId = deviceId;
            this.traceLog.deviceType = deviceType;
            this.traceLog.traceId = traceId;
            this.traceLog.requestId = requestId;
            this.traceLog.eventDeviceId = deviceId;
            return this;
        }
        // 请求的数据
        public PmsTraceLogBuilder requestParameter(String parentSpanId, String command, String requestContent) {
            this.traceLog.parentSpanId = parentSpanId;
            this.traceLog.spanId = UUID.randomUUID().toString();
            this.traceLog.command = command;
            this.traceLog.requestContent = requestContent;
            this.traceLog.requestTime = new Date();
            return this;
        }

        // 同步返回的数据
        public PmsTraceLogBuilder responseParameter(String responseContent) {
            this.traceLog.responseContent = responseContent;
            this.traceLog.responseTime = new Date();
            return this;
        }
        // 回调的数据
        public PmsTraceLogBuilder callbackParameter(String callType, String callbackContent, String eventId) {
            this.traceLog.callbackContent = callbackContent;
            this.traceLog.callbackTime = new Date();
            this.traceLog.isCallback = 1;
            this.traceLog.command = null;
            this.traceLog.callbackType = callType;
            this.traceLog.eventId = eventId;
            return this;
        }

        public HephaestusPmsTraceLog build() {
            return this.traceLog;
        }
    }
}
