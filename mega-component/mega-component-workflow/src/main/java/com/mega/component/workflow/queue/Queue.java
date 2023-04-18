package com.mega.component.workflow.queue;

import com.mega.component.workflow.models.ExecutableTask;
import java.io.Closeable;

public interface Queue extends Closeable
{
    void start();

    @Override
    void close();

    void put(ExecutableTask executableTask);
}
