package com.mega.component.nuc.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mega.component.nuc.device.AbstractDevice;

public class RequestParameterCommand<T> extends RequestCommand {

    @JsonProperty("Parameters")
    protected T parameters;

    public RequestParameterCommand(AbstractDevice device, AbstractCommand command) {
        super(device, command);
    }

    public RequestParameterCommand(AbstractDevice device, AbstractCommand command, String requestId) {
        super(device, command, requestId);
    }

    public RequestParameterCommand(AbstractDevice device, AbstractCommand command, T parameters) {
        super(device, command);
        this.parameters = parameters;
    }

    public RequestParameterCommand(AbstractDevice device, AbstractCommand command, String requestId, T parameters) {
        super(device, command, requestId);
        this.parameters = parameters;
    }

    public T getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "RequestParameterCommand{" +
                "command='" + command + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }
}
