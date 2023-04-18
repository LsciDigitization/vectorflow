package com.mega.component.workflow.queue;

import com.google.common.annotations.VisibleForTesting;
import com.mega.component.workflow.admin.WorkflowManagerState;
import java.io.Closeable;

public interface QueueConsumer extends Closeable
{
    void start();

    @Override
    void close();

    WorkflowManagerState.State getState();

    @VisibleForTesting
    void debugValidateClosed();
}
