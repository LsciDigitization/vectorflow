package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageBase {

    @JsonProperty("RequestId")
    protected String requestId;

    @JsonProperty("EventId")
    protected String eventId;

    @JsonProperty("DeviceId")
    protected String deviceId;

    // 指令名称，参考NUC Command
    @JsonProperty("Command")
    protected String command;

    // 数据(具体内容视情况而定)
    @JsonProperty("Data")
    protected String data;

    // 是否在调试，调试的不用上报PMS
    @JsonProperty("IsDebug")
    protected Boolean isDebug;


    @Override
    public String toString() {
        return "MessageBase{" +
                "requestId='" + requestId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", command='" + command + '\'' +
                ", data='" + data + '\'' +
                ", isDebug=" + isDebug +
                '}';
    }

}
