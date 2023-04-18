package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErrorEvent extends EventBase {

    @JsonProperty("ErrorType")
    public ErrorType errorType;

    // 设备错误影响
    @JsonProperty("DeviceErrorAffect")
    public DeviceErrorAffect deviceErrorAffect;

    // 容错优先级，越小越高
    @JsonProperty("Priority")
    public int priority;

    // Retry预估时间(单位ms，-1表示无限大)。若容错指令没有Retry，则为0
    @JsonProperty("RetryDuration")
    public int retryDuration;


}
