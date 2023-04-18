package com.mega.component.workflow.details.internalmodels;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.time.LocalDateTime;

public class StartedTask implements Serializable
{
    private final String instanceName;
    private final LocalDateTime startDateUtc;
    private final int progress;

    public StartedTask(String instanceName, LocalDateTime startDateUtc, int progress)
    {
        this.instanceName = Preconditions.checkNotNull(instanceName, "instanceName cannot be null");
        this.startDateUtc = Preconditions.checkNotNull(startDateUtc, "startDateUtc cannot be null");
        this.progress = progress;
    }

    public String getInstanceName()
    {
        return instanceName;
    }

    public LocalDateTime getStartDateUtc()
    {
        return startDateUtc;
    }
    
    public int getProgress()
    {
        return progress;
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

        StartedTask that = (StartedTask)o;

        if ( !instanceName.equals(that.instanceName) )
        {
            return false;
        }
        //noinspection RedundantIfStatement
        if ( !startDateUtc.equals(that.startDateUtc) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = instanceName.hashCode();
        result = 31 * result + startDateUtc.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "StartedTaskModel{" +
            "instanceName='" + instanceName + '\'' +
            ", startDateUtc=" + startDateUtc +
            ", progress=" + progress +
            '}';
    }
}
