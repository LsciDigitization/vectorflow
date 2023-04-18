package com.mega.component.workflow.events;

import org.apache.curator.framework.listen.Listenable;
import java.io.Closeable;

/**
 * A listener manager for events. Once {@link #start()} is called, workflow
 * events will be generated as runs and tasks are started, completed, etc.
 * When through, call {@link #close()}.
 */
public interface WorkflowListenerManager extends Closeable
{
    /**
     * Start listening for events. NOTE: previous events are not reported.
     * i.e. already started runs/tasks are not reported.
     */
    void start();

    /**
     * Return the container to add/remove event listeners
     *
     * @return container
     */
    Listenable<WorkflowListener> getListenable();
}
