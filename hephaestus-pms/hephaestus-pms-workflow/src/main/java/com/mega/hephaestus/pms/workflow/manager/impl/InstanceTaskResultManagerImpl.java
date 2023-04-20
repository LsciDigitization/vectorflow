package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskResultEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskResultService;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceTaskResultManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 13:39
 */
@Component
@RequiredArgsConstructor
public class InstanceTaskResultManagerImpl implements InstanceTaskResultManager {

    private final IInstanceTaskResultService instanceResultService;

    private final ExperimentGroupHistoryManager experimentGroupHistoryManager;

    public void createInstanceResult(long instanceId, String stepKey, String executionStatus,
                                     String executionResult) {

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            ProcessRecordEntity experimentGroupHistory = runningGroupOptional.get();

            InstanceTaskResultEntity instanceTaskResultEntity = new InstanceTaskResultEntity();
            instanceTaskResultEntity.setInstanceId(instanceId);
            instanceTaskResultEntity.setProcessRecordId(experimentGroupHistory.getId());
            instanceTaskResultEntity.setStepKey(stepKey);
            instanceTaskResultEntity.setExecutionResult(executionResult);
            instanceTaskResultEntity.setExecutionStatus(executionStatus);

            int version = 0;
            Optional<InstanceTaskResultEntity> lastInstanceResult = instanceResultService
                    .getLastByInstanceId(instanceId);
            if (lastInstanceResult.isPresent()) {
                version = lastInstanceResult.get().getVersion() + 1;
            }
            instanceTaskResultEntity.setVersion(version);
            instanceResultService.save(instanceTaskResultEntity);

        }


    }

}
