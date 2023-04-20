package com.mega.hephaestus.pms.workflow.task.taskbuild;

import com.mega.component.workflow.models.TaskType;

public class TaskRootBuilder implements TaskBuilder {

    public Task build(TaskType taskType) {
        Task task = new Task();
        task.setTaskId("root");
        task.setTaskType(taskType);
        return task;
    }

    public static TaskRootBuilder builder() {
        return new TaskRootBuilder();
    }

}
