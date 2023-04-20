package com.mega.hephaestus.pms.workflow.manager;

import com.mega.component.bioflow.flow.GraphStepTypeFilter;
import com.mega.component.bioflow.task.ProcessId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.workflow.manager.plan.StepManager;
import com.mega.component.bioflow.step.Experiment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/7 14:42
 */
public class GraphExperimentStepFilter extends GraphStepTypeFilter {

    private final Experiment experiment;

    public GraphExperimentStepFilter(ProjectId projectId, ProcessId processId, StepManager stepManager) {
        this.experiment = stepManager.getExperiment(projectId, processId);
    }

    public Experiment getExperiment() {
        return experiment;
    }

    @Override
    public List<StepType> getSteps() {
        return experiment.getSteps().stream()
                .map(v -> (StepType) v)
                .collect(Collectors.toList());
    }

    @Override
    public StepType getFirstStep() {
        return getSteps().get(0);
    }

    @Override
    public StepType getLastStep() {
        return getSteps().get(experiment.getSteps().size() - 1);
    }
}
