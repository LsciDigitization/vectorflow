package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Response extends ReplyMessageBase {

    // 预估时间(单位ms，-1表示无限大)。若指令拒绝，则无意义。
    // 容错指令Retry的Response.Duration，即为Error.RetryDuration。
    @JsonProperty("Duration")
    protected int duration;

    @JsonProperty("Parameters")
    private Object parameters;

    @JsonProperty("IsAccepted")
    private Boolean isAccepted;

    @Override
    public String toString() {
        return "Response{" +
                "requestId='" + this.requestId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", command='" + command + '\'' +
                ", data='" + data + '\'' +
                ", isDebug=" + isDebug +
                ", executeStatus=" + executeStatus +
                ", resultCode='" + resultCode + '\'' +
                ", message='" + message + '\'' +
                ", parameters='" + parameters + '\'' +
                ", isAccepted='" + isAccepted + '\'' +
                ", duration=" + duration +
                '}';
    }



}
