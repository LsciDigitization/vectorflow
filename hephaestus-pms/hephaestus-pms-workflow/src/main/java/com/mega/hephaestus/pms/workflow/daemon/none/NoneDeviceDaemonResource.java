package com.mega.hephaestus.pms.workflow.daemon.none;

import com.mega.component.json.JsonFacade;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import com.mega.hephaestus.pms.workflow.device.devicecommand.DeviceRequestTask;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.DaemonResourceInterface;
import com.mega.hephaestus.pms.workflow.device.devicelock.DeviceLockService;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.DeviceTaskManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceTaskManager;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskPool;
import com.mega.hephaestus.pms.workflow.task.stagetask.DeviceNowLock;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
@Deprecated(since = "20230331")
public class NoneDeviceDaemonResource implements DaemonResourceInterface {

    private final DeviceTaskPool deviceTaskPool;

    private final DeviceClientPool deviceClientPool;

    private final DeviceRequestTask deviceRequestTask;

    private final DeviceNowLock deviceNowLock;

    private final TaskLoggerService taskLoggerService;

    private final DeviceTaskManager deviceTaskManager;

    private final DeviceLockService deviceLockService;

    private final JsonFacade jsonFacade;

    private final DeviceResourceManager deviceResourceManager;

    private final InstanceTaskManager instanceTaskManager;

}
