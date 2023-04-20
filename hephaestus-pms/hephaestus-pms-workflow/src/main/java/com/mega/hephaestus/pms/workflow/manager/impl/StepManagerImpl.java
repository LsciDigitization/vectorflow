package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.bioflow.step.*;
import com.mega.component.bioflow.task.ExperimentId;
import com.mega.component.bioflow.task.ProcessId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.model.service.*;
import com.mega.hephaestus.pms.workflow.manager.model.StepConditionModel;
import com.mega.hephaestus.pms.workflow.manager.model.StepModel;
import com.mega.hephaestus.pms.workflow.manager.plan.StepManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class StepManagerImpl implements StepManager {
    // step service
    private final IFlowStepService stepService;

    // 步骤条件
    private final IFlowStepConditionService stepConditionService;

    // 步骤分支
    private final IFlowStepBranchService stepBranchService;

    //  branch service
    private final IFlowBranchService branchService;

    private final ILabwarePlateTypeService plateTypeService;

    @Override
    public List<StepModel> listByProjectId(ProjectId projectId) {

        List<FlowStepEntity> stepEntities = stepService.listByProjectId(projectId.getLongId());
        List<FlowStepConditionEntity> stepConditionEntities = stepConditionService.listByProjectId(projectId.getLongId());

        Map<String, List<FlowStepConditionEntity>> stepCondtionMap = stepConditionEntities.stream().collect(Collectors.groupingBy(FlowStepConditionEntity::getStepKey));

        return stepEntities.stream()
                .map(this::entityToModel)
                .peek(v -> {
                    String stepKey = v.getStepKey();
                    List<FlowStepConditionEntity> stepConditionList = stepCondtionMap.get(stepKey);

                    List<StepConditionModel> collect = stepConditionList.stream().map(this::entityToStepCondition).collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(collect)) {
                        v.setConditions(collect);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<StepModel> getByStepKey(ProjectId projectId, StepType stepType) {

        Optional<FlowStepEntity> stepEntityOptional = stepService.getByStepKey(projectId.getLongId(), stepType.getCode());
        if (stepEntityOptional.isPresent()) {
            FlowStepEntity flowStepEntity = stepEntityOptional.get();
            StepModel stepModel = entityToModel(flowStepEntity);
            List<FlowStepConditionEntity> stepConditionList = stepConditionService.ListByStepKey(projectId.getLongId(), stepType.getCode());
            if (CollectionUtils.isNotEmpty(stepConditionList)) {
                List<StepConditionModel> collect = stepConditionList.stream().map(this::entityToStepCondition).collect(Collectors.toList());
                stepModel.setConditions(collect);
            }
            return Optional.of(stepModel);
        }

        return Optional.empty();

    }

    /**
     * 拿到第一个step
     *
     * @param projectId 项目
     * @return 第一个step
     */
    @Override
    public Optional<StepModel> getFirstStep(ProjectId projectId) {
        List<StepModel> stepModelList = listByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(stepModelList)) {
            return stepModelList.stream().min(Comparator.comparing(StepModel::getSortOrder));
        }
        return Optional.empty();
    }

    @Override
    public List<ExperimentStep> listByProjectIdAndProcessId(ProjectId projectId, ProcessId processId) {
        List<FlowStepEntity> stepEntities = stepService.listByProjectId(projectId.getLongId());
        if (CollectionUtils.isNotEmpty(stepEntities)) {
            List<String> stepKeys = stepEntities.stream().map(FlowStepEntity::getStepKey).collect(Collectors.toList());

            // 拿到所有的分支
            List<FlowStepBranchEntity> flowStepBranchEntities = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(stepKeys)) {
                flowStepBranchEntities = stepBranchService.listByProjectIdAndProcessIdAndInStepKey(projectId.getLongId(), processId.getLongId(), stepKeys);
            }

            Map<String, List<FlowStepBranchEntity>> stepBranchMap = flowStepBranchEntities.stream().collect(Collectors.groupingBy(FlowStepBranchEntity::getStepKey));

            // 拿到 耗材
            Set<String> plateKeys = flowStepBranchEntities.stream().map(FlowStepBranchEntity::getPlateKey).collect(Collectors.toSet());
            List<LabwarePlateTypeEntity> plateTypes = plateTypeService.listByKeys(plateKeys);

            Map<String, List<LabwarePlateTypeEntity>> plateTypeMap = plateTypes.stream().collect(Collectors.groupingBy(LabwarePlateTypeEntity::getPlateKey));

            //拿到 branch
            List<FlowBranchEntity> list = branchService.list();
            // 排好序的branch
            List<String> branchOrderList = list.stream().sorted(Comparator.comparing(FlowBranchEntity::getSortOrder)).map(FlowBranchEntity::getBranchKey).collect(Collectors.toList());

            return stepEntities.stream()
                    .map(this::entityToStep)
                    .peek(v -> {
                        String stepKey = v.getStepKey();

                        // 处理分支步骤
                        if (stepBranchMap.containsKey(stepKey)) {

                            // step 分支关系表
                            List<FlowStepBranchEntity> stepBranchList = stepBranchMap.get(stepKey).stream().sorted(Comparator.comparing(FlowStepBranchEntity::getSortOrder).reversed()).collect(Collectors.toList());

                            if (CollectionUtils.isNotEmpty(stepBranchList)) {

                                // 同一个step下 按照 分支继续分组 分支排序
                                Map<String, List<FlowStepBranchEntity>> branchGroup = stepBranchList.stream()
                                        .collect(Collectors.groupingBy(FlowStepBranchEntity::getBranchKey,() -> new TreeMap<>(Comparator.comparing(o -> branchOrderList.indexOf(o))),Collectors.toList() ));


                                List<Branch> branches = new ArrayList<>();
                                branchGroup.forEach((k,stepBranchs) -> {

                                    Branch branch = new Branch();
                                    List<FlowStepBranchEntity> stepBranchEntityList = stepBranchs;
                                    if (CollectionUtils.isNotEmpty(stepBranchEntityList)) {
                                        FlowStepBranchEntity stepBranchEntity = stepBranchEntityList.get(0);
                                        branch.setId(String.valueOf(stepBranchEntity.getId()));
                                        branch.setName(v.getName());
                                        branch.setDescription(stepBranchEntity.getDescription());
                                        branch.setBranchKey(stepBranchEntity.getBranchKey() + "-" + stepBranchEntity.getPlateNo());
                                        branch.setBranchName(stepBranchEntity.getName());


                                        List<PlateLine> collect = stepBranchEntityList.stream().sorted(Comparator.comparing(FlowStepBranchEntity::getPlateSortOrder)).map(stepBranch -> {
                                            String plateKey = stepBranch.getPlateKey();
                                            if (plateTypeMap.containsKey(plateKey)) {
                                                List<LabwarePlateTypeEntity> plateTypeList = plateTypeMap.get(plateKey);
                                                if (CollectionUtils.isNotEmpty(plateTypeList)) {
                                                    String plateName = plateTypeList.get(0).getPlateName();
                                                    return new PlateLine(stepBranch.getBranchKey() + "-" + stepBranch.getId(),
                                                            plateName,
                                                            plateName,
                                                            stepBranch.getPlateIndex(), plateName);
                                                }
                                                return new PlateLine();
                                            }
                                            return new PlateLine();
                                        }).collect(Collectors.toList());
                                        branch.setPlates(collect);
                                    }


                                    branches.add(branch);
                                });
                                branchGroup.forEach((k, stepBranchEntityList) -> {

                                });
                                v.setBranches(branches);

                            }
                        }

                    }).collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public Experiment getExperiment(ProjectId projectId, ProcessId processId) {

        List<ExperimentStep> steps = listByProjectIdAndProcessId(projectId, processId);
        Experiment experiment = new Experiment();
        if (CollectionUtils.isNotEmpty(steps)) {
            experiment.setSteps(steps);
        }
        return experiment;
    }

    /**
     * 按照plateKey分组
     *
     * @param projectId 项目id
     * @param processId 流程id
     * @return 分组后的数据
     */
    @Override
    public Map<String, List<PlateLine>> groupByPlateKey(ProjectId projectId, ProcessId processId) {


        List<FlowStepBranchEntity> flowStepBranchEntities = stepBranchService.listByProjectIdAndProcessId(projectId.getLongId(), processId.getLongId());
        if (CollectionUtils.isNotEmpty(flowStepBranchEntities)) {
            Map<String, List<FlowStepBranchEntity>> collect = flowStepBranchEntities.stream()
                    .collect(Collectors.groupingBy(FlowStepBranchEntity::getPlateIndex));
            Map<String, List<PlateLine>> resultMap = new HashMap<>();
            collect.forEach((k, v) -> {
                List<FlowStepBranchEntity> collect1 = v.stream()
                        .sorted(Comparator.comparing(FlowStepBranchEntity::getSortOrder))
                        .collect(Collectors.toList());

                List<PlateLine> collect2 = collect1.stream().map(this::entityToPlateLine).collect(Collectors.toList());
                resultMap.put(k, collect2);

            });
            return resultMap;
        }
        return Map.of();
    }


    private Step entityToStep(FlowStepEntity entity) {
        Step step = new Step();
        step.setId(String.valueOf(entity.getId()));
        step.setName(entity.getName());
        step.setDescription(entity.getDescription());
        step.setStepKey(entity.getStepKey());
        step.setStepName(entity.getName());
        step.setLocation(StepLocation.toEnum(entity.getLocation().toUpperCase()));
        return step;
    }


    private Branch entityToBranch(FlowStepBranchEntity entity, Map<String, List<LabwarePlateTypeEntity>> plateMap) {

        String stepBranchId = String.valueOf(entity.getId());

        Branch branch = new Branch();
        branch.setId(stepBranchId);
        branch.setName(entity.getName());
        branch.setDescription(entity.getDescription());
        branch.setBranchKey(entity.getBranchKey());
        branch.setBranchName(entity.getName());
        return branch;

    }


    private PlateLine entityToPlateLine(FlowStepBranchEntity entity) {
        PlateLine plateLine = new PlateLine();
        plateLine.setId(entity.getBranchKey() + "-" + entity.getId());
        plateLine.setPlateKey(entity.getPlateIndex());
        return plateLine;
    }


    // step 实体 转换成model对象
    private StepModel entityToModel(FlowStepEntity entity) {
        StepModel stepModel = new StepModel();
        stepModel.setId(entity.getId());
        stepModel.setName(entity.getName());
        stepModel.setDescription(entity.getDescription());
        stepModel.setStepKey(entity.getStepKey());
        if (Objects.nonNull(entity.getProjectId())) {
            stepModel.setProjectId(new ProjectId(entity.getProjectId()));
        }

        if (Objects.nonNull(entity.getIsBottleneck())) {
            stepModel.setResourceBottleneck(entity.getIsBottleneck().toBoolean());
        }
        stepModel.setSortOrder(entity.getSortOrder());
        return stepModel;
    }

    // stepCondition 实体 转换成model对象
    private StepConditionModel entityToStepCondition(FlowStepConditionEntity entity) {
        StepConditionModel model = new StepConditionModel();
        model.setId(entity.getId());
        if (Objects.nonNull(entity.getProjectId())) {
            model.setProjectId(new ProjectId(entity.getProjectId()));
        }
        if (Objects.nonNull(entity.getExperimentId())) {
            model.setExperimentId(new ExperimentId(entity.getExperimentId()));
        }

        model.setStepKey(entity.getStepKey());
        model.setLabwareType(entity.getLabwareType());
        model.setLabwareCount(entity.getLabwareCount());
        model.setBranchKey(entity.getBranchKey());
        model.setLabwareArriveType(entity.getLabwareArriveType());

        return model;
    }

}
