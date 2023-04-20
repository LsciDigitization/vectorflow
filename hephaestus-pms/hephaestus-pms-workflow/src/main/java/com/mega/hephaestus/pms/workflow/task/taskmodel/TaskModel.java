package com.mega.hephaestus.pms.workflow.task.taskmodel;

import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.workflow.task.tasklock.TaskLockStatus;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/22 15:16
 */
public interface TaskModel {

    String getTaskName();

    String getTaskDescription();

    String getDeviceId();

    DeviceType getDeviceType();

    Command getTaskCommand();

    int getTaskTimeout();

    TaskLockStatus getLockStatus();

}
