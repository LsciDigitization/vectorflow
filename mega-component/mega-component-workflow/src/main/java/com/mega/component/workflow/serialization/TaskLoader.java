package com.mega.component.workflow.serialization;

import com.mega.component.workflow.models.Task;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Utility for loading Tasks from a file/stream
 */
public class TaskLoader
{
    /**
     * Load a complete task with children from a JSON stream
     *
     * @param jsonStream the JSON stream
     * @return task
     */
    public static Task load(Reader jsonStream)
    {
        try
        {
            return JsonSerializer.getTask(JsonSerializer.getMapper().readTree(jsonStream));
        }
        catch ( IOException e )
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load a complete task with children from a JSON string
     *
     * @param json the JSON
     * @return task
     */
    public static Task load(String json)
    {
        try ( StringReader reader = new StringReader(json) )
        {
            return load(reader);
        }
    }

    private TaskLoader()
    {
    }
}
