package com.mega.hephaestus.pms.nuc.workflow.task;

import com.mega.component.nuc.message.ErrorEvent;
import com.mega.component.nuc.message.Request;
import com.mega.component.nuc.message.Response;
import com.mega.component.nuc.message.WarningEvent;
import lombok.Data;

@Data
public class StoreData {
    // 任务ID
    private String instanceId;
    // 设备ID
    private String deviceId;
    // 请求ID
    private String requestId;
    // 任务阶段
    private String stage;
    // 请求消息
    private Request request;
    // 响应信息
    private Response response;
    // 错误消息
    private ErrorEvent error;
    // 警告消息
    private WarningEvent warning;
}
