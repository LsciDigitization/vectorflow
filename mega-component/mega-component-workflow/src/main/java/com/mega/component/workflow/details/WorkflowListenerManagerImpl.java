package com.mega.component.workflow.details;

import com.mega.component.workflow.events.WorkflowEvent;
import com.mega.component.workflow.events.WorkflowListener;
import com.mega.component.workflow.events.WorkflowListenerManager;
import com.mega.component.workflow.models.RunId;
import com.mega.component.workflow.models.TaskId;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.listen.StandardListenerManager;
import org.apache.curator.framework.listen.UnaryListenerManager;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.utils.CloseableUtils;

import java.io.IOException;

public class WorkflowListenerManagerImpl implements WorkflowListenerManager {
    private final PathChildrenCache completedTasksCache;
    private final PathChildrenCache startedTasksCache;
    private final PathChildrenCache runsCache;
    private final UnaryListenerManager<WorkflowListener> listenerContainer = StandardListenerManager.standard();

    public WorkflowListenerManagerImpl(WorkflowManagerImpl workflowManager) {
        completedTasksCache = new PathChildrenCache(workflowManager.getCurator(), ZooKeeperConstants.getCompletedTaskParentPath(), false);
        startedTasksCache = new PathChildrenCache(workflowManager.getCurator(), ZooKeeperConstants.getStartedTasksParentPath(), false);
        runsCache = new PathChildrenCache(workflowManager.getCurator(), ZooKeeperConstants.getRunParentPath(), false);
    }

    @Override
    public void start() {
        try {
            runsCache.getListenable().addListener((client, event) -> {
                if (event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                    RunId runId = new RunId(ZooKeeperConstants.getRunIdFromRunPath(event.getData().getPath()));
                    postEvent(new WorkflowEvent(WorkflowEvent.EventType.RUN_STARTED, runId));
                } else if (event.getType() == PathChildrenCacheEvent.Type.CHILD_UPDATED) {
                    RunId runId = new RunId(ZooKeeperConstants.getRunIdFromRunPath(event.getData().getPath()));
                    postEvent(new WorkflowEvent(WorkflowEvent.EventType.RUN_UPDATED, runId));
                }
            });

            startedTasksCache.getListenable().addListener((client, event) -> {
                if (event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                    RunId runId = new RunId(ZooKeeperConstants.getRunIdFromStartedTasksPath(event.getData().getPath()));
                    TaskId taskId = new TaskId(ZooKeeperConstants.getTaskIdFromStartedTasksPath(event.getData().getPath()));
                    postEvent(new WorkflowEvent(WorkflowEvent.EventType.TASK_STARTED, runId, taskId));
                }
            });

            completedTasksCache.getListenable().addListener((client, event) -> {
                if (event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                    RunId runId = new RunId(ZooKeeperConstants.getRunIdFromCompletedTasksPath(event.getData().getPath()));
                    TaskId taskId = new TaskId(ZooKeeperConstants.getTaskIdFromCompletedTasksPath(event.getData().getPath()));
                    postEvent(new WorkflowEvent(WorkflowEvent.EventType.TASK_COMPLETED, runId, taskId));
                }
            });

            runsCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            completedTasksCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            startedTasksCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        CloseableUtils.closeQuietly(runsCache);
        CloseableUtils.closeQuietly(startedTasksCache);
        CloseableUtils.closeQuietly(completedTasksCache);
    }

    @Override
    public Listenable<WorkflowListener> getListenable() {
        return listenerContainer;
    }

    private void postEvent(WorkflowEvent event) {
        listenerContainer.forEach(l -> {
            if (l != null) {
                l.receiveEvent(event);
            }
        });
    }
}
