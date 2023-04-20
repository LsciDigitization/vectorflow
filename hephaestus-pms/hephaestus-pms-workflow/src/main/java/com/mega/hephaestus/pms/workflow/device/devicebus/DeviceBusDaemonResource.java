package com.mega.hephaestus.pms.workflow.device.devicebus;

import com.mega.component.json.JsonFacade;
import com.mega.hephaestus.pms.nuc.config.WorkProperties;
import com.mega.hephaestus.pms.workflow.manager.dynamic.*;
import com.mega.hephaestus.pms.workflow.testutils.TaskTimeRateService;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.DaemonResourceInterface;
import com.mega.hephaestus.pms.workflow.device.devicelock.DeviceLockService;
import com.mega.hephaestus.pms.workflow.device.deviceschedule.DeviceSchedulePool;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskPool;
import com.mega.hephaestus.pms.workflow.task.stagetask.DeviceNowLock;
import com.mega.hephaestus.pms.workflow.device.devicecommand.DeviceRequestTask;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class DeviceBusDaemonResource implements DaemonResourceInterface {

    private final DeviceTaskPool deviceTaskPool;

    private final DeviceClientPool deviceClientPool;

    private final DeviceRequestTask deviceRequestTask;

    private final DeviceNowLock deviceNowLock;

    private final TaskLoggerService taskLoggerService;

    private final DeviceTaskManager deviceTaskManager;

    private final InstanceTaskManager instanceTaskManager;

    private final DeviceLockService deviceLockService;

    private final JsonFacade jsonFacade;

    private final DeviceResourceManager deviceResourceManager;

    //**************** private

    private final WorkProperties workProperties;

    private final InstanceStepManager instanceStepManager;

    private final TaskTimeRateService taskTimeRateService;

    private final WorkEventPusher workEventPusher;

    private final InstanceCapManager instanceCapManager;

    private final ExperimentGroupHistoryManager experimentGroupHistoryManager;

//    private final ExperimentGroupPoolManager experimentGroupPoolManager;

    private final ProcessLabwareManager processLabwareManager;
    private final DeviceSchedulePool deviceSchedulePool;

}
