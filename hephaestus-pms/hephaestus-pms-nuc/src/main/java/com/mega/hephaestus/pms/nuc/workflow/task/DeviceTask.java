package com.mega.hephaestus.pms.nuc.workflow.task;

import com.mega.component.nuc.message.*;
import com.mega.component.nuc.exceptions.DeviceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceTask {

    private final String instanceId;

    private final String deviceId;

    private final String requestId;

    private final StorageTask storage;

    private final String cacheKey;

    public DeviceTask(String instanceId, String deviceId, String requestId, StorageTask storage) {
        this.instanceId = instanceId;
        this.deviceId = deviceId;
        this.requestId = requestId;
        this.storage = storage;
        this.cacheKey = String.format("StageTask:device%s", deviceId);
    }

    public static DeviceTask of(String deviceId, String requestId, StorageTask storage) {
        return new DeviceTask(null, deviceId, requestId, storage);
    }

    public static DeviceTask of(String instanceId, String deviceId, String requestId, StorageTask storage) {
        return new DeviceTask(instanceId, deviceId, requestId, storage);
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public StorageTask getStorage() {
        return storage;
    }

    private String makeHashKey(String command) {
        return String.format("%s:%s", requestId, command);
    }

    public void save(String command, Response response) {
        storage.put(cacheKey, makeHashKey(command), response);
    }

    public void save(String command, EventBase event) {
        storage.put(cacheKey, makeHashKey(command), event);
    }

    public void save(String command, StoreData data) {
        storage.put(cacheKey, makeHashKey(command), data);
    }

    public void save(String command, String content) {
        storage.put(cacheKey, makeHashKey(command), content);
    }


    public Object get(String command) {
        return storage.get(cacheKey, makeHashKey(command));
    }

    /**
     * 设备回调检查
     * @param command 执行的命令
     * @return 返回是否
     */
    public boolean check(String command) {
        Object obj = get(command);
        if (obj instanceof ResponseEvent) {
            if (((ResponseEvent) obj).getExecuteStatus() == ExecuteStatus.Success) {
                log.info("设备运行回调完成 {}", obj);
                // 回调消息接收成功，跳出循环
                return true;
            }
        } else if (obj instanceof WarningEvent) {
            WarningEvent warningResponse = (WarningEvent) obj;
            log.info("设备运行回调警告 {}", warningResponse.getMessage());
            throw new DeviceException(warningResponse.getResultCode(), warningResponse.getMessage(), warningResponse);
        } else if (obj instanceof ErrorEvent) {
            ErrorEvent errorResponse = (ErrorEvent) obj;
            log.info("设备运行回调错误 {}", errorResponse.getMessage());
            throw new DeviceException(errorResponse.getResultCode(), errorResponse.getMessage(), errorResponse);
        } else {
            log.info("等待设备回调检测中...");
        }
        return false;
    }

}
