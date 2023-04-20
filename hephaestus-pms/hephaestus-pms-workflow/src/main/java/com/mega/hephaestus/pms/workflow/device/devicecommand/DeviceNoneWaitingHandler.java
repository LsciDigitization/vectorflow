package com.mega.hephaestus.pms.workflow.device.devicecommand;

import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceClient;
import com.mega.component.nuc.timing.Timing;
import com.mega.hephaestus.pms.workflow.testutils.TaskTimeRateService;
import com.mega.hephaestus.pms.workflow.task.taskmodel.BuiltinAwaitModel;
import com.mega.hephaestus.pms.workflow.task.stagetask.TaskParameterSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceNoneWaitingHandler {

    private final TaskParameterSerializer taskParameterSerializer;

    private final TaskTimeRateService taskTimeRateService;

    public void run(AbstractDevice device, DeviceClient deviceClient, Command command, ResourceTaskEntity deviceTask) {
        BuiltinAwaitModel.AwaitTaskParameter awaitTaskParameter = taskParameterSerializer.deserialize(deviceTask.getTaskParameter(), BuiltinAwaitModel.AwaitTaskParameter.class);
        log.info("DeviceNoneWaitingHandler {}", awaitTaskParameter);

        // 超时时间 taskTimeoutSecond
        int timeoutSecond = deviceTask.getTaskTimeoutSecond();

        // 模拟任务执行执行时间
        int waitDuration = Math.max(timeoutSecond, awaitTaskParameter.getWaitingTime());

        long sleepSecond2 = taskTimeRateService.getScaledDuration(waitDuration);

//        // 最小耗时1秒
//        if (waitDuration2 < 1) {
//            waitDuration2 = 1;
//        }
//
//        // 同步任务阻塞检测
//        TaskSpinWait.of(5, (int) waitDuration2)
//                .wait(() -> false);

        try {
            // 小于0.3秒
            Timing.of(sleepSecond2 * 1000, TimeUnit.MILLISECONDS).sleepMin(100);

//            double sleepSecond3;
//            if (sleepSecond2 < 0.3) {
//                sleepSecond3 = 0.3 * 1000;
//            } else {
//                sleepSecond3 = (double) sleepSecond2 * 1000;
//            }
//
//            Thread.sleep(TimeUnit.MILLISECONDS.toMillis((long) sleepSecond3));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
