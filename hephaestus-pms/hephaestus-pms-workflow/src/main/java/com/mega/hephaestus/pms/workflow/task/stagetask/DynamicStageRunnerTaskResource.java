package com.mega.hephaestus.pms.workflow.task.stagetask;

import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceTaskManager;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/22 16:18
 */
@Component
@Data
@RequiredArgsConstructor
public class DynamicStageRunnerTaskResource {

    private final InstanceTaskManager instanceTaskManager;

    private final TaskLoggerService stageLoggerService;

    private final WorkEventPusher workEventPusher;

}
