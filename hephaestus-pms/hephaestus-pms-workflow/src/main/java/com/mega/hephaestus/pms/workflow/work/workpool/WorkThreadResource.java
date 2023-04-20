package com.mega.hephaestus.pms.workflow.work.workpool;

import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentInstanceNewManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStageManager;
import com.mega.hephaestus.pms.workflow.task.stageflow.StageFlowManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Getter
@RequiredArgsConstructor
public class WorkThreadResource {

    @Autowired
    private StageFlowManager stageFlowManager;
    @Autowired
    private ExperimentStageManager experimentStageManager;
    @Autowired
    private ExperimentInstanceNewManager experimentInstanceNewManager;
    @Resource
    private ThreadPoolExecutor workflowExecutor;
}
