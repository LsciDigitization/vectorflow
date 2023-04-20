package com.mega.hephaestus.pms.workflow.task.taskmodel;


import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.workflow.task.tasklock.TaskLockStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BuiltinConditionModel implements TaskModel, Serializable {

    private String taskName = "条件任务";

    private String taskDescription = "执行条件任务";

    private String deviceId = "0";

    private DeviceType deviceType;

    private Command taskCommand;

    private ConditionTaskParameter taskParameter = new ConditionTaskParameter();

    // 秒
    private int taskTimeout = 600;

    private TaskLockStatus lockStatus = TaskLockStatus.DEFAULT;

    @Data
    public static class ConditionTaskParameter implements Serializable {
        // 来自哪个步骤的输出结果
        private StepTypeEnum stepType;
        // 条件类型
        private ConditionOperator operator;
        // 判断条件
        private List<ConditionValue> values;

    }

    public enum ConditionOperator {
        Between,
    }

    public static class ConditionValue {
        private double value;
    }

    public static class BetweenCondition implements Rule {
        private ConditionValue min;
        private ConditionValue max;

        public boolean condition(double value) {
            return false;
        }
    }


    public interface Rule {
        boolean condition(double value);
    }


}
