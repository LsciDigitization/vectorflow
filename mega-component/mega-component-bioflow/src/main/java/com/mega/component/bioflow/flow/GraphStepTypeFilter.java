package com.mega.component.bioflow.flow;

import com.mega.component.nuc.step.StepType;
import com.mega.component.nuc.step.StepTypeEnum;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/7 14:40
 */
public class GraphStepTypeFilter implements StepTypeFilter<StepType> {

    public List<StepType> getSteps() {
        return List.of(
                StepTypeEnum.STEP1, StepTypeEnum.STEP2, StepTypeEnum.STEP3, StepTypeEnum.STEP4,
                StepTypeEnum.STEP5, StepTypeEnum.STEP6, StepTypeEnum.STEP7, StepTypeEnum.STEP8,
                StepTypeEnum.STEP9, StepTypeEnum.STEP10, StepTypeEnum.STEP11, StepTypeEnum.STEP12,
                StepTypeEnum.STEP13
        );
    }

    public StepType getFirstStep() {
        return getSteps().get(0);
    };

    public StepType getLastStep() {
        return getSteps().get(getSteps().size() - 1);
    };

}
