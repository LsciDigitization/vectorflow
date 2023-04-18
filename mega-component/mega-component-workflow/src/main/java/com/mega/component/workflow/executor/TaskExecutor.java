package com.mega.component.workflow.executor;

import com.mega.component.workflow.WorkflowManager;
import com.mega.component.workflow.models.ExecutableTask;

/**
 * Factory for creating task executions
 */
@FunctionalInterface
public interface TaskExecutor
{
    /**
     * Create a task execution for the given task
     *
     * @param workflowManager the manager
     * @param executableTask the task
     * @return the execution
     */
    TaskExecution newTaskExecution(WorkflowManager workflowManager, ExecutableTask executableTask);
}
