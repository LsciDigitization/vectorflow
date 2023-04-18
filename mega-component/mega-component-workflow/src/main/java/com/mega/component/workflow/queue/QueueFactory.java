package com.mega.component.workflow.queue;

import com.mega.component.workflow.details.WorkflowManagerImpl;
import com.mega.component.workflow.models.TaskType;

public interface QueueFactory
{
    Queue createQueue(WorkflowManagerImpl workflowManager, TaskType taskType);
    QueueConsumer createQueueConsumer(WorkflowManagerImpl workflowManager, TaskRunner taskRunner, TaskType taskType);
}
