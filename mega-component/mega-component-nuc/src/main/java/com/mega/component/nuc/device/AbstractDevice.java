package com.mega.component.nuc.device;

import com.mega.component.nuc.command.*;
import com.mega.component.nuc.exceptions.DeviceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractDevice {

    /**
     * 设备ID
     */
    private final String deviceId;

    private final String deviceKey;

    private final DeviceType deviceType;


    /**
     * 设备命令组
     */
    private final Map<String, AbstractCommand> commands = new HashMap<>();

    public AbstractDevice(DeviceType deviceType, String deviceId) {
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.deviceKey = deviceType + "-" + deviceId;
    }

    public AbstractDevice(DeviceType deviceType, String deviceId, String deviceKey) {
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.deviceKey = deviceKey;
    }

    /**
     * 获取设备ID
     *
     * @return 设备ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * 添加命令
     *
     * @param command 命令对象
     */
    public void addCommand(AbstractCommand command) {
        commands.put(command.getKey().toString(), command);
    }

    /**
     * 获取命令
     *
     * @param key 命令KEY
     * @return 命令对象
     */
    public AbstractCommand getCommand(String key) {
        if (!commands.containsKey(key)) {
            throw new DeviceException(String.format("该设备未注册该命令%s", key));
        }
        return commands.get(key);
    }

    /**
     * 获取所有命令列表
     *
     * @return Command List
     */
    public List<AbstractCommand> getCommands() {
        return (List<AbstractCommand>) commands.values();
    }


    /**
     * 请求一个命令
     *
     * @param command Command
     * @return RequestCommand
     */
    public RequestCommand newRequest(Command command) {
        return new RequestCommand(this, getCommand(command.toString()));
    }

    /**
     * 请求一个命令，携带一个请求ID
     *
     * @param command Command
     * @return RequestCommand
     */
    public RequestCommand newRequest(Command command, String requestId) {
        return new RequestCommand(this, getCommand(command.toString()), requestId);
    }

    /**
     * 请求一个命令，并发送，返回结果
     *
     * @param command Command
     * @return RequestCommand
     */
    public <R> R sendRequest(Command command, Function<RequestCommandInterface, R> callback) {
        RequestCommand requestCommand = new RequestCommand(this, getCommand(command.toString()));
        return callback.apply(requestCommand);
    }

    /**
     * 请求一个命令，携带一个请求ID，并发送，返回结果
     *
     * @param command Command
     * @return RequestCommand
     */
    public <R> R sendRequest(Command command, String requestId, Function<RequestCommandInterface, R> callback) {
        RequestCommand requestCommand = new RequestCommand(this, getCommand(command.toString()), requestId);
        return callback.apply(requestCommand);
    }

    public <T, R> R sendRequestParameter(Command command, Function<RequestCommandInterface, R> callback, Supplier<T> supplier) {
        RequestParameterCommand<T> requestCommand = new RequestParameterCommand<>(this, getCommand(command.toString()), supplier.get());
        return callback.apply(requestCommand);
    }

    public <T, R> R sendRequestParameter(Command command, String requestId, Function<RequestCommandInterface, R> callback, Supplier<T> supplier) {
        RequestParameterCommand<T> requestCommand = new RequestParameterCommand<>(this, getCommand(command.toString()), requestId, supplier.get());
        return callback.apply(requestCommand);
    }


    @Override
    public String toString() {
        return "AbstractDevice{" +
                "deviceId='" + deviceId + '\'' +
                ", commands=" + commands +
                '}';
    }
}
