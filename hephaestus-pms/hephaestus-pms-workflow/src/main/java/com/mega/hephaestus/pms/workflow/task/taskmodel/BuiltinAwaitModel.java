package com.mega.hephaestus.pms.workflow.task.taskmodel;


import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.hephaestus.pms.nuc.customize.CustomizeCommand;
import com.mega.hephaestus.pms.workflow.task.tasklock.TaskLockStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class BuiltinAwaitModel implements TaskModel, Serializable {

    private String taskName = "等待任务";

    private String taskDescription = "执行等待任务";

    private String deviceId = "0";

    private DeviceTypeEnum deviceType = DeviceTypeEnum.None;

    private Command taskCommand = CustomizeCommand.Waiting;

    private AwaitTaskParameter taskParameter = new AwaitTaskParameter();

    // 秒
    private int taskTimeout = 600;

    private TaskLockStatus lockStatus = TaskLockStatus.DEFAULT;

    @Data
    public static class AwaitTaskParameter implements Serializable {
        // 等待时间
        private int waitingTime;

    }


}
