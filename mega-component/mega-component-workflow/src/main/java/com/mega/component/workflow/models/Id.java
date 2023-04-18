package com.mega.component.workflow.models;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.UUID;

/**
 * Base for IDs
 */
public class Id implements Serializable
{
    private final String id;

    protected Id()
    {
        id = newRandomId();
    }

    static String newRandomId()
    {
        return UUID.randomUUID().toString();
    }

    protected Id(String id)
    {
        this.id = Preconditions.checkNotNull(id, "id cannot be null");
    }

    public String getId()
    {
        return id;
    }

    public boolean isValid()
    {
        return (id.length() > 0);
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

        Id id1 = (Id)o;

        //noinspection RedundantIfStatement
        if ( !id.equals(id1.id) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public String toString()
    {
        return "Id{" +
            "id='" + id + '\'' +
            '}';
    }
}
