package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventBase {

    @JsonProperty("EventDeviceId")
    private String eventDeviceId;

    @JsonProperty("ExecuteStatus")
    private Integer executeStatus;

    @JsonProperty("State")
    private Integer state;

    @JsonProperty("ResultCode")
    private String resultCode;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("EventId")
    private String eventId;

    @JsonProperty("DeviceId")
    private String deviceId;

    @JsonProperty("Command")
    private String command;

    @JsonProperty("Parameters")
    private Object parameters;

    @JsonProperty("IsDebug")
    private Boolean isDebug;

    @JsonProperty("Data")
    private Object data;

}
