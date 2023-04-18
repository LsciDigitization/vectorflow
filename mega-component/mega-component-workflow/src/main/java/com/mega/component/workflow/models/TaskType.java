package com.mega.component.workflow.models;

import com.google.common.base.Preconditions;
import java.io.Serializable;

/**
 * <p>
 *     Models a task type
 * </p>
 *
 * <p>
 *     Tasks can be idempotent or non-idempotent. Idempotent tasks
 *     can be restarted/re-executed when a workflow instance crashes
 *     or there is some other kind of error. non-idempotent tasks
 *     will only be attempted once.
 * </p>
 */
public class TaskType implements Serializable
{
    private final String type;
    private final String version;
    private final boolean isIdempotent;
    private final TaskMode mode;

    // for backward compatibility
    public TaskType(String type, String version, boolean isIdempotent)
    {
        this(type, version, isIdempotent, TaskMode.STANDARD);
    }

    /**
     * @param type any value to represent the task type
     * @param version the version of this task type
     * @param isIdempotent whether or not this task is idempotent (see class description for details)
     * @param mode the mode
     */
    public TaskType(String type, String version, boolean isIdempotent, TaskMode mode)
    {
        Preconditions.checkArgument(!type.contains("/"), "type cannot contain '/'");
        Preconditions.checkArgument(!version.contains("/"), "version cannot contain '/'");
        this.mode = Preconditions.checkNotNull(mode, "mode cannot be null");

        this.version = Preconditions.checkNotNull(version, "version cannot be null");
        this.type = Preconditions.checkNotNull(type, "type cannot be null");
        this.isIdempotent = isIdempotent;
    }

    public String getVersion()
    {
        return version;
    }

    public String getType()
    {
        return type;
    }

    public boolean getIsIdempotent()
    {
        return isIdempotent;
    }

    public TaskMode getMode()
    {
        return mode;
    }

    @SuppressWarnings("SimplifiableIfStatement")
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

        TaskType taskType = (TaskType)o;

        if ( isIdempotent != taskType.isIdempotent )
        {
            return false;
        }
        if ( !type.equals(taskType.type) )
        {
            return false;
        }
        if ( !version.equals(taskType.version) )
        {
            return false;
        }
        return mode == taskType.mode;

    }

    @Override
    public int hashCode()
    {
        int result = type.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + (isIdempotent ? 1 : 0);
        result = 31 * result + mode.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "TaskType{" +
            "type='" + type + '\'' +
            ", version='" + version + '\'' +
            ", isIdempotent=" + isIdempotent +
            ", mode=" + mode +
            '}';
    }
}
