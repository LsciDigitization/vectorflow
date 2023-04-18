package com.mega.component.workflow.events;

@FunctionalInterface
public interface WorkflowListener
{
    /**
     * Receive a workflow event
     *
     * @param event the event
     */
    void receiveEvent(WorkflowEvent event);
}
