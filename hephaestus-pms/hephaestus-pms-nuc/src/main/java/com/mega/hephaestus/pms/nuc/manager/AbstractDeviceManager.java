package com.mega.hephaestus.pms.nuc.manager;

import com.mega.component.nuc.bus.GetParametersCommand;
import com.mega.component.nuc.bus.SetParametersCommand;
import com.mega.hephaestus.pms.nuc.config.DeviceConfiguration;
import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceClient;
import com.mega.component.nuc.message.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDeviceManager<D extends AbstractDevice, C extends DeviceClient> {

    protected final D device;

    protected final C client;

    protected final DeviceConfiguration configuration;

    public AbstractDeviceManager(D device, C client, DeviceConfiguration configuration) {
        this.device = device;
        this.client = client;
        this.configuration = configuration;
    }

    public D getDevice() {
        return device;
    }

    public C getClient() {
        return client;
    }

    public DeviceConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * 获取设备配置参数
     * @param parameterKey 配置Key
     * @return 返回参数值响应
     */
    public Response getParameters(AbstractCommand.Parameter parameterKey) {
        return device.sendRequestParameter(BusCommand.GetParameters, client::executeCommand, () -> {
            return GetParametersCommand.GetParametersBuilder.create()
                    .addParameter(parameterKey)
                    .build();
        });
    }

    /**
     * 设置设备配置参数
     * @param parameterKey 配置Key
     * @param object 参数值，类型不固定
     * @return 返回标准响应
     */
    public Response setParameters(AbstractCommand.Parameter parameterKey, Object object) {
        return device.sendRequestParameter(BusCommand.SetParameters, client::executeCommand, () -> {
            return SetParametersCommand.SetParametersBuilder.create()
                    .addParameter(parameterKey, object)
                    .build();
        });
    }


    // BUS
    public Response reset() {
        return device.sendRequest(BusCommand.Reset, client::executeCommand);
    }

    public Response pause() {
        return device.sendRequest(BusCommand.Pause, client::executeCommand);
    }

    public Response continue_() {
        return device.sendRequest(BusCommand.Continue, client::executeCommand);
    }

    public Response abort() {
        return device.sendRequest(BusCommand.Abort, client::executeCommand);
    }

    public Response close_() {
        return device.sendRequest(BusCommand.Close, client::executeCommand);
    }

    public Response getCommands() {
        return device.sendRequest(BusCommand.GetCommands, client::executeCommand);
    }

    public Response getGetParameters() {
        return device.sendRequest(BusCommand.GetGetParameters, client::executeCommand);
    }

    public Response getSetParameters() {
        return device.sendRequest(BusCommand.GetSetParameters, client::executeCommand);
    }

    public Response getState() {
        return device.sendRequest(BusCommand.GetState, client::executeCommand);
    }

    public Response getDeviceState() {
        return device.sendRequest(BusCommand.GetDeviceState, client::executeCommand);
    }

    public Response getDeviceInfo() {
        return device.sendRequest(BusCommand.GetDeviceInfo, client::executeCommand);
    }

    public Response getErrors() {
        return device.sendRequest(BusCommand.GetErrors, client::executeCommand);
    }

    public Response getWarnings() {
        return device.sendRequest(BusCommand.GetWarnings, client::executeCommand);
    }

    public Response shutdown_() {
        return device.sendRequest(BusCommand.Shutdown, client::executeCommand);
    }

    public Response reboot() {
        return device.sendRequest(BusCommand.Reboot, client::executeCommand);
    }

    public Response getTasks() {
        return device.sendRequest(BusCommand.GetTasks, client::executeCommand);
    }

    public Response getTaskState(String requestId) {
        return device.sendRequestParameter(BusCommand.GetTaskState, client::executeCommand, () -> {
            return SetParametersCommand.SetParametersBuilder.create()
                    .addParameter("TargetRequestId", requestId)
                    .build();
        });
    }

    public Response pauseTask(String requestId) {
        return device.sendRequestParameter(BusCommand.PauseTask, client::executeCommand, () -> {
            return SetParametersCommand.SetParametersBuilder.create()
                    .addParameter("TargetRequestId", requestId)
                    .build();
        });
    }

    public Response continueTask(String requestId) {
        return device.sendRequestParameter(BusCommand.ContinueTask, client::executeCommand, () -> {
            return SetParametersCommand.SetParametersBuilder.create()
                    .addParameter("TargetRequestId", requestId)
                    .build();
        });
    }

    public Response abortTask(String requestId) {
        return device.sendRequestParameter(BusCommand.AbortTask, client::executeCommand, () -> {
            return SetParametersCommand.SetParametersBuilder.create()
                    .addParameter("TargetRequestId", requestId)
                    .build();
        });
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("deviceId", configuration.getDeviceId());
        map.put("deviceType", configuration.getDeviceType().toString());
        map.put("deviceAlias", configuration.getDeviceAlias());
        map.put("deviceKey", configuration.getDeviceKey());
        map.put("url", configuration.getUrl());
        map.put("callbackUrl", configuration.getCallbackUrl());
        return map;
    }

}
