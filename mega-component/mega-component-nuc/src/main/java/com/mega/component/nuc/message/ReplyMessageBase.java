package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReplyMessageBase extends MessageBase {

    // 粗码
    @JsonProperty("ExecuteStatus")
    protected ExecuteStatus executeStatus;

    // 状态码，8位16进制
    @JsonProperty("ResultCode")
    protected String resultCode;

    // 状态码描述
    @JsonProperty("Message")
    protected String message;

    @Override
    public String toString() {
        return "ReplyMessageBase{" +
                "requestId='" + this.requestId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", command='" + command + '\'' +
                ", data='" + data + '\'' +
                ", isDebug=" + isDebug +
                ", executeStatus=" + executeStatus +
                ", resultCode='" + resultCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


}
