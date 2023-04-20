package com.mega.hephaestus.pms.workflow.work.workstep;

import com.mega.component.nuc.step.StepType;
import lombok.Data;

import java.util.Date;

@Data
public class Step {

    // 关键步骤名称
    private String name;

    // 步骤类型
    private StepType type;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 状态
    private StepStatus status;

    // 到下一个步骤消耗时间周期，单位秒
    private long nextStepDurationSecond;

}
