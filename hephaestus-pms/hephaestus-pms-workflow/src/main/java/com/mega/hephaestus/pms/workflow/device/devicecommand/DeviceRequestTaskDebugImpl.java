package com.mega.hephaestus.pms.workflow.device.devicecommand;

import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.component.nuc.command.Command;
import com.mega.hephaestus.pms.nuc.config.condition.NucDeviceDebugCondition;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceClient;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.component.nuc.timing.Timing;
import com.mega.hephaestus.pms.nuc.customize.CustomizeCommand;
import com.mega.hephaestus.pms.workflow.event.DeviceRunningEvent;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.testutils.TaskTimeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
@Conditional(NucDeviceDebugCondition.class)
public class DeviceRequestTaskDebugImpl implements DeviceRequestTask {

    private final DeviceNoneWaitingHandler deviceNoneWaitingHandler;

    private final DeviceNoneRunScriptHandler deviceNoneRunScriptHandler;

    private final TaskTimeRateService taskTimeRateService;

    private final WorkEventPusher workEventPusher;

    private void run(StepType stepType, AbstractDevice device, DeviceClient deviceClient, Command command, ResourceTaskEntity deviceTask) {
        log.info("任务进入测试中......");

        log.info("DeviceTask 执行测试中 {}", deviceTask);

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

        // 超时时间 taskTimeoutSecond
        int timeoutSecond = deviceTask.getTaskTimeoutSecond();

        long sleepSecond2 = taskTimeRateService.getScaledDuration(timeoutSecond);

        try {
            // 小于0.3秒
            Timing.of(sleepSecond2 * 1000, TimeUnit.MILLISECONDS).sleepMin(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run(DeviceRequestTaskParameter deviceRequestTaskParameter) {
        run(deviceRequestTaskParameter.getStepType(), deviceRequestTaskParameter.getDevice(), deviceRequestTaskParameter.getDeviceClient(), deviceRequestTaskParameter.getCommand(), deviceRequestTaskParameter.getDeviceTask());
    }

}
