package com.mega.component.bioflow.step;

import com.mega.component.nuc.step.StepType;
import lombok.Data;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 21:06
 */
@Data
public class Step implements ExperimentStep, StepType {
    private String id;
    private String name;
    private String description;
    private String stepKey;
    private String stepName;
    private StepLocation location;
    private List<Branch> branches;

    public Step() {
    }

    public Step(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Step(String id, String name, String description, String stepKey, String stepName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stepKey = stepKey;
        this.stepName = stepName;
    }

    public Step(String id, String name, String description, String stepKey, String stepName, StepLocation location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stepKey = stepKey;
        this.stepName = stepName;
        this.location = location;
    }

    public void addBranch(Branch branch) {
        this.branches.add(branch);
    }

    public void addBranches(List<Branch> branches) {
        this.branches.addAll(branches);
    }

    public void removeBranch(Branch branch) {
        this.branches.remove(branch);
    }

    public void removeBranch(String branchKey) {
        this.branches.removeIf(branch -> branch.getBranchKey().equals(branchKey));
    }

    @Override
    public ExperimentStepType getType() {
        return ExperimentStepType.STEP;
    }

    @Override
    public String name() {
        return stepKey;
    }

    @Override
    public String getLabel() {
        return stepName;
    }

    @Override
    public String getCode() {
        return stepKey;
    }

    @Override
    public long getValue() {
        return 0;
    }

    @Override
    public long getStepTotal() {
        return 0;
    }
}
