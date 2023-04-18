package com.mega.component.workflow.models;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mega.component.workflow.WorkflowManager;
import com.mega.component.workflow.executor.TaskExecutionStatus;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * Models an execution result.
 */
public class TaskExecutionResult implements Serializable
{
    private final TaskExecutionStatus status;
    private final String message;
    private final Map<String, String> resultData;
    private final LocalDateTime completionTimeUtc;
    private final Optional<RunId> subTaskRunId;

    /**
     * @param status the execution status
     * @param message any message that task
     */
    public TaskExecutionResult(TaskExecutionStatus status, String message)
    {
        this(status, message, Maps.newHashMap(), null, null);
    }

    /**
     * @param status the execution status
     * @param message any message that task
     * @param resultData result data (can be accessed via {@link WorkflowManager#getTaskExecutionResult(RunId, TaskId)})
     */
    public TaskExecutionResult(TaskExecutionStatus status, String message, Map<String, String> resultData)
    {
        this(status, message, resultData, null, null);
    }

    /**
     * @param status the execution status
     * @param message any message that task
     * @param resultData result data (can be accessed via {@link WorkflowManager#getTaskExecutionResult(RunId, TaskId)})
     * @param subTaskRunId if not null, the value of a sub-task started via {@link WorkflowManager#submitSubTask(RunId, Task)}. If
     *                     a sub-task was started, it's vital that the run ID be passed here so that this run can pause until the sub-task
     *                     completes.
     */
    public TaskExecutionResult(TaskExecutionStatus status, String message, Map<String, String> resultData, RunId subTaskRunId)
    {
        this(status, message, resultData, subTaskRunId, null);
    }

    /**
     * @param status the execution status
     * @param message any message that task
     * @param resultData result data (can be accessed via {@link WorkflowManager#getTaskExecutionResult(RunId, TaskId)})
     * @param subTaskRunId if not null, the value of a sub-task started via {@link WorkflowManager#submitSubTask(RunId, Task)}. If
     *                     a sub-task was started, it's vital that the run ID be passed here so that this run can pause until the sub-task
     *                     completes.
     * @param completionTimeUtc the completion time of the task. If null, <code>LocalDateTime.now(Clock.systemUTC())</code> is used
     */
    public TaskExecutionResult(TaskExecutionStatus status, String message, Map<String, String> resultData, RunId subTaskRunId, LocalDateTime completionTimeUtc)
    {
        this.message = Preconditions.checkNotNull(message, "message cannot be null");
        this.status = Preconditions.checkNotNull(status, "status cannot be null");
        this.subTaskRunId = Optional.ofNullable(subTaskRunId);
        this.completionTimeUtc = (completionTimeUtc != null) ? completionTimeUtc : LocalDateTime.now(Clock.systemUTC());

        resultData = Preconditions.checkNotNull(resultData, "resultData cannot be null");
        this.resultData = ImmutableMap.copyOf(resultData);
    }

    public String getMessage()
    {
        return message;
    }

    public TaskExecutionStatus getStatus()
    {
        return status;
    }

    public Map<String, String> getResultData()
    {
        return resultData;
    }

    public Optional<RunId> getSubTaskRunId()
    {
        return subTaskRunId;
    }

    public LocalDateTime getCompletionTimeUtc()
    {
        return completionTimeUtc;
    }

    @Override
    public boolean equals(Object o)
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        TaskExecutionResult that = (TaskExecutionResult)o;

        if ( !completionTimeUtc.equals(that.completionTimeUtc) )
        {
            return false;
        }
        if ( !message.equals(that.message) )
        {
            return false;
        }
        if ( !resultData.equals(that.resultData) )
        {
            return false;
        }
        if ( status != that.status )
        {
            return false;
        }
        //noinspection RedundantIfStatement
        if ( !subTaskRunId.equals(that.subTaskRunId) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = status.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + resultData.hashCode();
        result = 31 * result + completionTimeUtc.hashCode();
        result = 31 * result + subTaskRunId.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "TaskExecutionResult{" +
            "status=" + status +
            ", message='" + message + '\'' +
            ", resultData=" + resultData +
            ", completionTimeUtc=" + completionTimeUtc +
            ", subTaskRunId=" + subTaskRunId +
            '}';
    }
}
