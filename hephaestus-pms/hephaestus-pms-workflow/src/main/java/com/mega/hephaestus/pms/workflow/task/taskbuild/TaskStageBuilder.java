package com.mega.hephaestus.pms.workflow.task.taskbuild;

import com.mega.component.workflow.models.TaskType;

public class TaskStageBuilder implements TaskBuilder {

    private final String taskId;

    public TaskStageBuilder(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public Task build(TaskType taskType) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskType(taskType);
        return task;
    }

    public static TaskStageBuilder builder(String taskId) {
        return new TaskStageBuilder(taskId);
    }

}
