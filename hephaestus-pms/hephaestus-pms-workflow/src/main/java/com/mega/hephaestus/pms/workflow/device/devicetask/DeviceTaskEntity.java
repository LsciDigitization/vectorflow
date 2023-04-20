package com.mega.hephaestus.pms.workflow.device.devicetask;

import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.DeviceType;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * 设备任务实体
 */
@Data
public class DeviceTaskEntity {

    // 设备类型
    private DeviceType deviceType;

    // 设备KEY
    private String deviceKey;

    // 请求命令
    private Command command;

    // 请求ID，唯一标识
    private String requestId;

    // 实验实例ID
    private Long instanceId;

    // 实验任务ID
    private Long taskId;

    // 请求参数
    private Map<String, Object> taskParameterMap;

    // 任务超时时间
    private int timeoutSecond;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceTaskEntity that = (DeviceTaskEntity) o;
        return deviceKey.equals(that.deviceKey) && requestId.equals(that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceKey, requestId);
    }
}
