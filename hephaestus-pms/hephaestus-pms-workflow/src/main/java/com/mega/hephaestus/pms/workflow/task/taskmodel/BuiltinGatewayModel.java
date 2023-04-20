package com.mega.hephaestus.pms.workflow.task.taskmodel;

import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.hephaestus.pms.nuc.customize.CustomizeCommand;
import com.mega.hephaestus.pms.workflow.task.tasklock.TaskLockStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class BuiltinGatewayModel implements TaskModel, Serializable {

    private String taskName = "判断任务";

    private String taskDescription = "执行远程脚本判断任务";

    private String deviceId = "0";

    private DeviceTypeEnum deviceType = DeviceTypeEnum.None;

    private Command taskCommand = CustomizeCommand.RunScript;

    private GatewayTaskParameter taskParameter = new GatewayTaskParameter();

    // 秒
    private int taskTimeout = 600;

    private TaskLockStatus lockStatus = TaskLockStatus.NOW_LOCK;

    @Data
    public static class GatewayTaskParameter implements Serializable {
        // 脚本类型
        private String scriptType = "python";
        // 远程脚本
        private String scriptFile;
    }

}
