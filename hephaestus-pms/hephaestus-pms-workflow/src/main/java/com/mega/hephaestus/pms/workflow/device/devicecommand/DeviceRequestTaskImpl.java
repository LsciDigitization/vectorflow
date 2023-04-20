package com.mega.hephaestus.pms.workflow.device.devicecommand;

import com.mega.component.json.JsonFacade;
import com.mega.component.nuc.step.StepType;
import com.mega.component.task.task.TaskSpinWait;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.component.nuc.command.Command;
import com.mega.hephaestus.pms.nuc.config.condition.NucDeviceNoDebugCondition;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceClient;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.component.nuc.exceptions.DeviceException;
import com.mega.component.nuc.message.ExecuteStatus;
import com.mega.component.nuc.message.Response;
import com.mega.hephaestus.pms.nuc.customize.CustomizeCommand;
import com.mega.hephaestus.pms.nuc.workflow.task.DeviceTask;
import com.mega.hephaestus.pms.nuc.workflow.task.StorageTask;
import com.mega.hephaestus.pms.workflow.event.DeviceRunningEvent;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Primary
@Conditional(NucDeviceNoDebugCondition.class)
public class DeviceRequestTaskImpl implements DeviceRequestTask {

    private final StorageTask storageTask;

    private final JsonFacade jsonFacade;

    private final DeviceNoneWaitingHandler deviceNoneWaitingHandler;

    private final DeviceNoneRunScriptHandler deviceNoneRunScriptHandler;

    private final WorkEventPusher workEventPusher;

    @Deprecated(since = "20230417")
    private void run(final AbstractDevice device, DeviceClient deviceClient, Command command, final String requestId, final Map<String, Object> taskParameterMap) {
        Response response;
        if (taskParameterMap == null) {
            response = device.sendRequest(command, requestId,
                    deviceClient::executeCommand);
        } else {
            response = device.sendRequestParameter(command, requestId,
                    deviceClient::executeCommand,
                    () -> taskParameterMap);
        }

        if (response.getExecuteStatus() == ExecuteStatus.Reject) {
            throw new DeviceException(device.getDeviceType().getCode(), response.getResultCode(), response.getMessage(), response);
        }

        // 异步任务阻塞检测
        TaskSpinWait.of().waitWithThrow(() -> {
            DeviceTask storage = DeviceTask.of(response.getDeviceId(), response.getRequestId(), storageTask);
            return storage.check(response.getCommand());
        });
    }

    public void run(StepType stepType, AbstractDevice device, DeviceClient deviceClient, Command command, ResourceTaskEntity deviceTask) {
        log.info("DeviceTask 执行中 {}", deviceTask);

        // DeviceType.None 特殊设备控制
        if (deviceTask.getDeviceType().equals(DeviceTypeEnum.None.getCode())) {
            if (deviceTask.getTaskCommand().equals(CustomizeCommand.Waiting.name())) {
                deviceNoneWaitingHandler.run(device, deviceClient, command, deviceTask);
                return;
            } else if (deviceTask.getTaskCommand().equals(CustomizeCommand.RunScript.name())) {
                deviceNoneRunScriptHandler.run(device, deviceClient, command, deviceTask);
                return;
            }
        }

        if (Objects.nonNull(stepType)) {
            // 发送设备运行事件
            workEventPusher.sendDeviceRunningEvent(new DeviceRunningEvent(this, stepType, device, command, deviceTask));
        }

        Optional<Map<String, Object>> taskParameterMapOptional = jsonFacade.toMap(deviceTask.getTaskParameter());
        Response response;
        if (taskParameterMapOptional.isEmpty()) {
            response = device.sendRequest(command, deviceTask.getTaskRequestId(),
                    deviceClient::executeCommand);
        } else {
            response = device.sendRequestParameter(command, deviceTask.getTaskRequestId(),
                    deviceClient::executeCommand,
                    taskParameterMapOptional::get);
        }

        if (response.getExecuteStatus() == ExecuteStatus.Reject) {
            throw new DeviceException(device.getDeviceType().getCode(), response.getResultCode(), response.getMessage(), response);
        }

        // 异步任务阻塞检测
        TaskSpinWait.of().waitWithThrow(() -> {
            DeviceTask storage = DeviceTask.of(response.getDeviceId(), response.getRequestId(), storageTask);
            return storage.check(response.getCommand());
        });
    }

    @Override
    public void run(DeviceRequestTaskParameter deviceRequestTaskParameter) {
        run(deviceRequestTaskParameter.getStepType(), deviceRequestTaskParameter.getDevice(), deviceRequestTaskParameter.getDeviceClient(), deviceRequestTaskParameter.getCommand(), deviceRequestTaskParameter.getDeviceTask());
    }

}
