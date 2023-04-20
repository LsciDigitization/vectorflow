package com.mega.hephaestus.pms.workflow.manager;

import com.mega.component.json.JsonFacade;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstanceStepBack;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceStepBackService;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;


/**
 * 实验实例关键步骤操作
 */
@Component
@RequiredArgsConstructor
@Deprecated(since = "20221202")
public class ExperimentInstanceStepBackManager {

    private final IInstanceStepBackService stepService;

    private final IInstanceService instanceService;

    private final JsonFacade jsonFacade;

    /**
     * Step 开始
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */
    @Deprecated(since = "20221202")
    public boolean startStep(long instanceId, Step step) {
        // 获取instanceStep
        Optional<HephaestusInstanceStepBack> instanceStepOptional = stepService.getByInstanceId(instanceId);
        HephaestusInstanceStepBack instanceStep;
        if (instanceStepOptional.isPresent()) {
            instanceStep = instanceStepOptional.get();
        } else {
            instanceStep = new HephaestusInstanceStepBack();
            instanceStep.setInstanceId(instanceId);
        }

        // 获取instance实例数据
        InstanceEntity instance = instanceService.getById(instanceId);
        if (Objects.nonNull(instance)) {
            if (Objects.nonNull(step.getType())) {
                if (StringUtils.isNotBlank(step.getType().getCode())) {

                    // 填充数据toJsonString
                    InstanceEntity newInstanceEntity = new InstanceEntity();
                    newInstanceEntity.setId(instance.getId());

                    // 计算从step1 到当前的总数
                    long sum = step.getType().getStepTotal();
                    // 填充数据toJsonString

                    Optional<String> stepOptional = jsonFacade.toJsonString(step);
                    if (stepOptional.isPresent()) {
                        newInstanceEntity.setCurrentStep(stepOptional.get());
                    }
                    newInstanceEntity.setCurrentStepTotal(sum);
                    instanceService.updateById(newInstanceEntity);
                }
            }

            instanceStep.setGroupId(instance.getProcessId());
            instanceStep.setExperimentId(instance.getExperimentId());
            instanceStep.setGroupHistoryId(instance.getProcessRecordId());

        }

        //数据填充
        instanceStep.setStepName(step.getName());
        instanceStep.setStepType(step.getType().toString());
        instanceStep.setStepStartTime(step.getStartTime());
        instanceStep.setStepEndTime(step.getEndTime());
        instanceStep.setStepStatus(step.getStatus().getCode());
        instanceStep.setNextStepDurationSecond(step.getNextStepDurationSecond());
        return stepService.saveOrUpdate(instanceStep);
    }


    /**
     * Step 结束
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */
    @Deprecated(since = "20221202")
    public boolean endStep(long instanceId, Step step) {
        Optional<HephaestusInstanceStepBack> instanceStepOptional = stepService.getByInstanceId(instanceId);

        if (instanceStepOptional.isPresent()) {
            HephaestusInstanceStepBack instanceStep = instanceStepOptional.get();

            //数据填充
            instanceStep.setStepEndTime(step.getEndTime());
            instanceStep.setStepStatus(step.getStatus().getCode());
            instanceStep.setNextStepDurationSecond(step.getNextStepDurationSecond());
            return stepService.updateById(instanceStep);
        }
        return false;
    }

}
