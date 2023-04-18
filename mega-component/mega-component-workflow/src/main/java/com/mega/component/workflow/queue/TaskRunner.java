package com.mega.component.workflow.queue;

import com.mega.component.workflow.models.ExecutableTask;

@FunctionalInterface
public interface TaskRunner
{
    void executeTask(ExecutableTask executableTask);
}
