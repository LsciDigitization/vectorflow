package com.mega.hephaestus.pms.workflow.task.taskbuild;

import com.mega.component.workflow.models.TaskType;

public class TaskEndBuilder implements TaskBuilder {

    public Task build(TaskType taskType) {
        Task task = new Task();
        task.setTaskId("end");
        task.setTaskType(taskType);
        return task;
    }

    public static TaskEndBuilder builder() {
        return new TaskEndBuilder();
    }

}
