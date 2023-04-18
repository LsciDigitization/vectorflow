package com.mega.component.workflow.details.internalmodels;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.mega.component.workflow.models.TaskId;
import java.io.Serializable;
import java.util.Collection;

public class RunnableTaskDag implements Serializable
{
    private final TaskId taskId;
    private final Collection<TaskId> dependencies;

    public RunnableTaskDag(TaskId taskId, Collection<TaskId> dependencies)
    {
        this.taskId = Preconditions.checkNotNull(taskId, "taskId cannot be null");
        dependencies = Preconditions.checkNotNull(dependencies, "dependencies cannot be null");
        this.dependencies = ImmutableSet.copyOf(dependencies);
    }

    public TaskId getTaskId()
    {
        return taskId;
    }

    public Collection<TaskId> getDependencies()
    {
        return dependencies;
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

        RunnableTaskDag that = (RunnableTaskDag)o;

        if ( !dependencies.equals(that.dependencies) )
        {
            return false;
        }
        //noinspection RedundantIfStatement
        if ( !taskId.equals(that.taskId) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = taskId.hashCode();
        result = 31 * result + dependencies.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "RunnableTaskDagEntryModel{" +
            "taskId=" + taskId +
            ", dependencies=" + dependencies +
            '}';
    }
}
