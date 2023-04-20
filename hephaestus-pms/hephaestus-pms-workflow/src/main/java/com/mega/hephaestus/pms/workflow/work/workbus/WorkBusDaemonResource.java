package com.mega.hephaestus.pms.workflow.work.workbus;

import com.mega.component.json.JsonFacade;
import com.mega.hephaestus.pms.nuc.config.WorkProperties;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.manager.dynamic.*;
import com.mega.hephaestus.pms.workflow.task.stageflow.ExperimentFlowStart;
import com.mega.hephaestus.pms.workflow.testutils.TaskTimeRateService;
import com.mega.hephaestus.pms.workflow.device.devicebottleneck.DeviceBottleneckPool;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.DaemonResourceInterface;
import com.mega.hephaestus.pms.workflow.device.devicelock.DeviceLockService;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.manager.plan.*;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskPool;
import com.mega.hephaestus.pms.workflow.device.devicecommand.DeviceRequestTask;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlatePool;
import com.mega.hephaestus.pms.workflow.work.workstart.WorkStartPool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class WorkBusDaemonResource implements DaemonResourceInterface {

    private final DeviceTaskPool deviceTaskPool;

    private final DeviceClientPool deviceClientPool;

    private final DeviceRequestTask deviceRequestTask;

//    private final ExperimentManager experimentManager;

    private final ExperimentFlowStart experimentFlowStart;

    private final WorkProperties workProperties;

//    private final DeviceNowLock deviceNowLock;

    private final TaskLoggerService taskLoggerService;

    private final DeviceTaskManager deviceTaskManager;

    private final DeviceLockService deviceLockService;

    private final JsonFacade jsonFacade;

    private final DeviceResourceManager deviceResourceManager;

    private final InstanceTaskManager instanceTaskManager;

    //****************** private

    private final ExperimentGroupHistoryManager experimentGroupHistoryManager;

//    private final ExperimentGroupPoolManager experimentGroupPoolManager;

    private final ProcessLabwareManager processLabwareManager;


    //    private final InstancePlateManager instancePlateManager;
    private final InstanceLabwareManager instanceLabwareManager;

    private final ExperimentStageTaskManager experimentStageTaskManager;

    private final ExperimentInstanceNewManager experimentInstanceNewManager;

    private final WorkEventPusher workEventPusher;

    private final InstanceStepManager instanceStepManager;

    private final TaskTimeRateService taskTimeRateService;

    private final WorkPlatePool workPlatePool;

    private final DeviceBottleneckPool deviceBottleneckPool;

    private final WorkStartPool workStartPool;

    private final StepManager stepManager;

    private final ResourceGroupManager resourceGroupManager;

    private final ExperimentProperties experimentProperties;

    private final ProcessIterationManager processIterationManager;

}
