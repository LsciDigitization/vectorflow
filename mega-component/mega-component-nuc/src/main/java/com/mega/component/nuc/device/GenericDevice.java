package com.mega.component.nuc.device;

import com.mega.component.nuc.bus.*;
import com.mega.component.nuc.command.AbstractCommand;

public class GenericDevice extends AbstractDevice {

    public GenericDevice(DeviceType deviceType, String deviceId) {
        super(deviceType, deviceId);
    }

    public GenericDevice(DeviceType deviceType, String deviceId, String deviceKey) {
        super(deviceType, deviceId, deviceKey);
    }

    private void buildCommand() {
        //BUS
        this.addCommand(new ResetCommand());
        this.addCommand(new PauseCommand());
        this.addCommand(new ContinueCommand());
        this.addCommand(new AbortCommand());
        this.addCommand(new CloseCommand());
        this.addCommand(new GetParametersCommand());
        this.addCommand(new SetParametersCommand());
        this.addCommand(new GetStateCommand());
        this.addCommand(new GetCommandsCommand());
        this.addCommand(new GetGetParametersCommand());
        this.addCommand(new GetSetParametersCommand());
        this.addCommand(new GetDeviceStateCommand());
        this.addCommand(new GetDeviceInfoCommand());
        this.addCommand(new GetErrorsCommand());
        this.addCommand(new GetWarningsCommand());
        this.addCommand(new ShutdownCommand());
        this.addCommand(new RebootCommand());
        this.addCommand(new GetTasksCommand());
        this.addCommand(new GetTaskStateCommand());
        this.addCommand(new PauseTaskCommand());
        this.addCommand(new ContinueTaskCommand());
        this.addCommand(new AbortTaskCommand());
    }

    public static GenericDevice of(DeviceType deviceType, String deviceId) {
        return ofNew(deviceType, deviceId);
    }

    public static GenericDevice of(DeviceType deviceType, String deviceId, String deviceKey) {
        return ofNew(deviceType, deviceId, deviceKey);
    }

    public static GenericDevice ofNew(DeviceType deviceType, String deviceId) {
        return new GenericDevice(deviceType, deviceId);
    }

    public static GenericDevice ofNew(DeviceType deviceType, String deviceId, String deviceKey) {
        return new GenericDevice(deviceType, deviceId, deviceKey);
    }

    public enum GenericParameter {

        BootConfig(new AbstractCommand.Parameter("BootConfig", "BootConfig的Json序列化字符串")),
        IsSimulation(new AbstractCommand.Parameter("IsSimulation", "是否为模拟模式")),
        NUCVisibility(new AbstractCommand.Parameter("NUCVisibility", "NUC显示状态")),
        HasBalloonTip(new AbstractCommand.Parameter("HasBalloonTip", "是否有系统提示")),
        Language(new AbstractCommand.Parameter("Language", "语言")),
        PMSAddress(new AbstractCommand.Parameter("PMSAddress", "PMS服务地址")),
        ;

        private final AbstractCommand.Parameter parameter;

        GenericParameter(AbstractCommand.Parameter parameter) {
            this.parameter = parameter;
        }

        public AbstractCommand.Parameter parameter() {
            return parameter;
        }

        @Override
        public String toString() {
            return parameter.getKey();
        }
    }
}
