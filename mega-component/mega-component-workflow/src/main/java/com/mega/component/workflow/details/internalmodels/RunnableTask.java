package com.mega.component.workflow.details.internalmodels;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.RunId;
import com.mega.component.workflow.models.TaskId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RunnableTask implements Serializable
{
    private final Map<TaskId, ExecutableTask> tasks;
    private final List<RunnableTaskDag> taskDags;
    private final LocalDateTime startTimeUtc;
    private final Optional<LocalDateTime> completionTimeUtc;
    private final Optional<RunId> parentRunId;

    public RunnableTask(Map<TaskId, ExecutableTask> tasks, List<RunnableTaskDag> taskDags, LocalDateTime startTimeUtc)
    {
        this(tasks, taskDags, startTimeUtc, null, null);
    }

    public RunnableTask(Map<TaskId, ExecutableTask> tasks, List<RunnableTaskDag> taskDags, LocalDateTime startTimeUtc, LocalDateTime completionTimeUtc)
    {
        this(tasks, taskDags, startTimeUtc, completionTimeUtc, null);
    }

    public RunnableTask(Map<TaskId, ExecutableTask> tasks, List<RunnableTaskDag> taskDags, LocalDateTime startTimeUtc, LocalDateTime completionTimeUtc, RunId parentRunId)
    {
        this.startTimeUtc = Preconditions.checkNotNull(startTimeUtc, "startTimeUtc cannot be null");
        this.completionTimeUtc = Optional.ofNullable(completionTimeUtc);
        this.parentRunId = Optional.ofNullable(parentRunId);
        tasks = Preconditions.checkNotNull(tasks, "tasks cannot be null");
        taskDags = Preconditions.checkNotNull(taskDags, "taskDags cannot be null");

        this.tasks = ImmutableMap.copyOf(tasks);
        this.taskDags = ImmutableList.copyOf(taskDags);
    }

    public Map<TaskId, ExecutableTask> getTasks()
    {
        return tasks;
    }

    public List<RunnableTaskDag> getTaskDags()
    {
        return taskDags;
    }

    public Optional<LocalDateTime> getCompletionTimeUtc()
    {
        return completionTimeUtc;
    }

    public LocalDateTime getStartTimeUtc()
    {
        return startTimeUtc;
    }

    public Optional<RunId> getParentRunId()
    {
        return parentRunId;
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

        RunnableTask that = (RunnableTask)o;

        if ( !completionTimeUtc.equals(that.completionTimeUtc) )
        {
            return false;
        }
        if ( !parentRunId.equals(that.parentRunId) )
        {
            return false;
        }
        if ( !startTimeUtc.equals(that.startTimeUtc) )
        {
            return false;
        }
        if ( !taskDags.equals(that.taskDags) )
        {
            return false;
        }
        //noinspection RedundantIfStatement
        if ( !tasks.equals(that.tasks) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = tasks.hashCode();
        result = 31 * result + taskDags.hashCode();
        result = 31 * result + startTimeUtc.hashCode();
        result = 31 * result + completionTimeUtc.hashCode();
        result = 31 * result + parentRunId.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "RunnableTask{" +
            "tasks=" + tasks +
            ", taskDags=" + taskDags +
            ", startTime=" + startTimeUtc +
            ", completionTime=" + completionTimeUtc +
            ", parentRunId=" + parentRunId +
            '}';
    }
}
