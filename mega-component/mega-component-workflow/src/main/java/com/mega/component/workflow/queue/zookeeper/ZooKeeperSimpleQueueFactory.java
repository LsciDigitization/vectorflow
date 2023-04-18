package com.mega.component.workflow.queue.zookeeper;

import com.mega.component.workflow.details.WorkflowManagerImpl;
import com.mega.component.workflow.models.TaskType;
import com.mega.component.workflow.queue.Queue;
import com.mega.component.workflow.queue.QueueConsumer;
import com.mega.component.workflow.queue.QueueFactory;
import com.mega.component.workflow.queue.TaskRunner;

public class ZooKeeperSimpleQueueFactory implements QueueFactory
{
    @Override
    public Queue createQueue(WorkflowManagerImpl workflowManager, TaskType taskType)
    {
        return internalCreateQueue(workflowManager, taskType, null);
    }

    @Override
    public QueueConsumer createQueueConsumer(WorkflowManagerImpl workflowManager, TaskRunner taskRunner, TaskType taskType)
    {
        ZooKeeperSimpleQueue queue = internalCreateQueue(workflowManager, taskType, taskRunner);
        return queue.getQueue();
    }

    private ZooKeeperSimpleQueue internalCreateQueue(WorkflowManagerImpl workflowManager, TaskType taskType, TaskRunner taskRunner)
    {
        return new ZooKeeperSimpleQueue(taskRunner, workflowManager.getSerializer(), workflowManager.getCurator(), taskType);
    }
}
