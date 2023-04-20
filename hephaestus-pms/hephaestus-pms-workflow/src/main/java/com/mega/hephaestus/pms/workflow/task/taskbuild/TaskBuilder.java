package com.mega.hephaestus.pms.workflow.task.taskbuild;

import com.mega.component.workflow.models.TaskType;

public interface TaskBuilder {
    Task build(TaskType taskType);
}
