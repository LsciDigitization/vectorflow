package com.mega.hephaestus.pms.workflow.device.devicedaemon;

import com.mega.component.json.JsonFacade;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import com.mega.hephaestus.pms.workflow.device.devicelock.DeviceLockService;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.DeviceTaskManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceTaskManager;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskPool;
import com.mega.hephaestus.pms.workflow.task.stagetask.DeviceNowLock;
import com.mega.hephaestus.pms.workflow.device.devicecommand.DeviceRequestTask;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;

public interface DaemonResourceInterface {

    DeviceTaskPool getDeviceTaskPool();

    DeviceClientPool getDeviceClientPool();

    DeviceRequestTask getDeviceRequestTask();

//    DeviceNowLock getDeviceNowLock();

    TaskLoggerService getTaskLoggerService();

    DeviceTaskManager getDeviceTaskManager();

    DeviceLockService getDeviceLockService();

    JsonFacade getJsonFacade();

    DeviceResourceManager getDeviceResourceManager();

    InstanceTaskManager getInstanceTaskManager();
}
