package com.mega.component.workflow.queue.zookeeper;

import com.google.common.base.Preconditions;
import com.mega.component.workflow.details.ZooKeeperConstants;
import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.Task;
import com.mega.component.workflow.models.TaskType;
import com.mega.component.workflow.queue.Queue;
import com.mega.component.workflow.queue.TaskRunner;
import com.mega.component.workflow.serialization.Serializer;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperSimpleQueue implements Queue
{
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SimpleQueue queue;
    private final Serializer serializer;

    ZooKeeperSimpleQueue(TaskRunner taskRunner, Serializer serializer, CuratorFramework client, TaskType taskType)
    {
        this.serializer = Preconditions.checkNotNull(serializer, "serializer cannot be null");
        String path = ZooKeeperConstants.getQueuePath(taskType);
        String lockPath = ZooKeeperConstants.getQueueLockPath(taskType);
        queue = new SimpleQueue(client, taskRunner, serializer, path, lockPath, taskType.getMode(), taskType.getIsIdempotent());
    }

    @Override
    public void start()
    {
        // NOP
    }

    @Override
    public void close()
    {
        // NOP
    }

    @Override
    public void put(ExecutableTask executableTask)
    {
        long value = 0;
        try
        {
            String valueStr = executableTask.getMetaData().get(Task.META_TASK_SUBMIT_VALUE);
            if ( valueStr != null )
            {
                value = Long.parseLong(valueStr);
            }
        }
        catch ( NumberFormatException ignore )
        {
            // ignore
        }

        try
        {
            byte[] bytes = serializer.serialize(executableTask);
            queue.put(bytes, value);
        }
        catch ( Exception e )
        {
            log.error("Could not add to queue for: " + executableTask, e);
            throw new RuntimeException(e);
        }
    }

    SimpleQueue getQueue()
    {
        return queue;
    }

    Serializer getSerializer()
    {
        return serializer;
    }
}
