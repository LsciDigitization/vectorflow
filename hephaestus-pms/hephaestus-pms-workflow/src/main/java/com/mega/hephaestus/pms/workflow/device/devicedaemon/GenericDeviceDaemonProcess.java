package com.mega.hephaestus.pms.workflow.device.devicedaemon;

import com.mega.component.nuc.step.GenericStepType;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.nuc.client.GenericDeviceClient;
import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.command.GenericCommand;
import com.mega.component.nuc.device.GenericDevice;
import com.mega.hephaestus.pms.nuc.manager.GenericDeviceManager;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicecommand.DeviceRequestTask;
import com.mega.hephaestus.pms.workflow.exception.DeviceTaskException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class GenericDeviceDaemonProcess implements Runnable {

    private final DaemonResourceInterface daemonResource;

    private final String deviceKey;

    public GenericDeviceDaemonProcess(DaemonResourceInterface daemonResource, String deviceKey) {
        this.daemonResource = daemonResource;
        this.deviceKey = deviceKey;
    }

    public void run() {

        // 获取任务对象 DeviceTask
        Optional<ResourceTaskEntity> deviceTaskOptional = daemonResource.getDeviceLockService().getRunningTask(deviceKey);
        deviceTaskOptional.ifPresent(deviceTask -> {
            try {
                // 获取设备管理对象 GenericDeviceManager
                Optional<GenericDeviceManager> deviceManagerOptional = daemonResource.getDeviceClientPool().get(deviceKey);
                deviceManagerOptional.ifPresent(genericDeviceManager -> {
                    log.info("接受任务 {}", genericDeviceManager.toMap());

                    GenericDevice device = genericDeviceManager.getDevice();
                    GenericDeviceClient client = genericDeviceManager.getClient();
                    Command command = GenericCommand.toEnum(deviceTask.getTaskCommand());
                    String requestId = deviceTask.getTaskRequestId();

                    // 设备执行任务开始日志记录
                    daemonResource.getTaskLoggerService().info(deviceTask.getInstanceId(), deviceTask, new FormattedMessage("设备执行任务开始 {} {}", device.getDeviceType(), device.getDeviceId()));

                    daemonResource.getDeviceTaskManager().runningTask(requestId);

                    // 修改 device 为运行状态
                    DeviceRequestTask.DeviceRequestTaskParameter deviceRequestTaskParameter = new DeviceRequestTask.DeviceRequestTaskParameter();

                    if (Objects.nonNull(deviceTask.getStepKey())) {
                        StepType stepType = GenericStepType.toEnum(deviceTask.getStepKey());
                        deviceRequestTaskParameter.setStepType(stepType);
                    }

                    deviceRequestTaskParameter.setDevice(device);
                    deviceRequestTaskParameter.setDeviceClient(client);
                    deviceRequestTaskParameter.setCommand(command);
                    deviceRequestTaskParameter.setDeviceTask(deviceTask);
                    daemonResource.getDeviceRequestTask().run(deviceRequestTaskParameter);

                    // 标记任务完成
                    daemonResource.getDeviceTaskManager().finishedTask(requestId);

                    // 运行完成，设备设为空闲状态
                    daemonResource.getDeviceResourceManager().idleDeviceResource(deviceKey);

                    // 设备执行任务完成日志记录
                    daemonResource.getTaskLoggerService().info(deviceTask.getInstanceId(), deviceTask, new FormattedMessage("设备执行任务完成 {} {}", device.getDeviceType(), device.getDeviceId()));
                });
            } catch (DeviceTaskException e) {
                // 标记任务失败
                daemonResource.getDeviceTaskManager().failedTask(deviceTask.getTaskRequestId(), e.getMessage());
            } finally {
                // 修改 device 为空闲状态
                daemonResource.getDeviceResourceManager().idleDeviceResource(deviceTask.getDeviceKey());
            }
        });

        // 检测deviceKey有没有任务运行
        List<DeviceResourceModel> runningDevices = daemonResource.getDeviceResourceManager().getRunningDevices();
        runningDevices.forEach(deviceResourceModel -> {
            Optional<ResourceTaskEntity> runningTaskOptional = daemonResource.getDeviceTaskManager().getRunningTask(deviceResourceModel.getDeviceKey());
            if (runningTaskOptional.isEmpty()) {
                // 运行完成，设备设为空闲状态
                daemonResource.getDeviceResourceManager().idleDeviceResource(deviceKey);
            }
        });

    }


}
