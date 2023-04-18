package com.mega.component.nuc.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mega.component.nuc.device.AbstractDevice;

import java.util.UUID;

public class RequestCommand implements RequestCommandInterface {

    @JsonProperty("Command")
    protected String command;

    @JsonProperty("DeviceId")
    protected String deviceId;

    @JsonProperty("RequestId")
    protected String requestId;

    public RequestCommand(AbstractDevice device, AbstractCommand command) {
        this(device, command, UUID.randomUUID().toString());
    }

    public RequestCommand(AbstractDevice device, AbstractCommand command, String requestId) {
        this.requestId = requestId;
        this.command = command.getKey().toString();
        this.deviceId = device.getDeviceId();
    }

    public String getCommand() {
        return command;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "RequestCommand{" +
                "command='" + command + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
