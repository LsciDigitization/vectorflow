package com.mega.hephaestus.pms.workflow.listener;

import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.component.nuc.plate.GenericPlateType;
import com.mega.hephaestus.pms.workflow.event.ProcessIterationConsumeEvent;
import com.mega.hephaestus.pms.workflow.event.StartInstancePlateEvent;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ProcessLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.model.StepConditionModel;
import com.mega.hephaestus.pms.workflow.manager.model.StepModel;
import com.mega.hephaestus.pms.workflow.manager.plan.ProcessIterationManager;
import com.mega.hephaestus.pms.workflow.manager.plan.StepManager;
import com.mega.hephaestus.pms.workflow.work.workstart.InstancePlateStarter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 17:31
 */
@Component
@Slf4j
public class ProcessIterationConsumeListener {

    @Resource
    private StepManager stepManager;
    @Resource
    private ProcessLabwareManager processLabwareManager;
    @Resource
    private ProcessIterationManager processIterationManager;
    @Resource
    private InstancePlateStarter instancePlateStarter;
    @Resource
    private WorkEventPusher workEventPusher;

    @EventListener
    public void processIterationConsumeEvent(ProcessIterationConsumeEvent event) {
        System.out.println("ProcessIterationConsumeEvent=========\n" + event);

        ProjectId projectId = event.getProjectId();
        ProcessRecordId processRecordId = event.getProcessRecordId();

        List<ProcessLabwareEntity> processLabwares = processLabwareManager.getProcessLabwares(processRecordId.getLongId());
        ProcessLabwareEntity mainEntity = processLabwares.stream()
                .filter(v -> v.getIsMain().toBoolean())
                .findFirst()
                .orElse(null);

        if (Objects.isNull(mainEntity)) {
            log.info("实验组 {} 未找到主板", processRecordId);
            return;
        }

        // 获取主板资源
        GenericPlateType genericPlateType = new GenericPlateType(mainEntity.getLabwareType());
        long mainPlateSize = instancePlateStarter.getWorkPlateConsumer(genericPlateType).size(processRecordId.getLongId());
        log.info("板池中主板剩余资源计算 mainPlateSize {}", mainPlateSize);
        if (mainPlateSize == 0) {
            log.info("板池中主板都为空mainPlateSize {}，忽略启动瓶颈资源调度计算", mainPlateSize);
            return;
        }

        // 启动封装
        instancePlateStarter.startBatch(() -> {
            instancePlateStarter.startInstancePlate(processRecordId, genericPlateType, event.getIterationNo());
        });

        // 获取Step排序第一个
        Optional<StepModel> firstStep = stepManager.getFirstStep(projectId);
        if (firstStep.isEmpty()) {
            log.info("实验组 {} 未找到Step", event.getProcessRecordId());
            return;
        }

        StepModel stepModel = firstStep.get();
        List<StepConditionModel> conditions = stepModel.getConditions();
        Map<String, List<StepConditionModel>> branchMap = conditions.stream()
                .collect(Collectors.groupingBy(StepConditionModel::getBranchKey, Collectors.toList()));
        branchMap.forEach((k, models) -> {
            log.info("branchKey {} conditions {}", k, models);
            models.stream()
                    .filter(v -> ! v.getLabwareType().equals(mainEntity.getLabwareType()))
                    .forEach(v -> {
                        log.info("branchKey {} condition {}", k, v);
                        // 通量消费
                        StartInstancePlateEvent event1 = new StartInstancePlateEvent(this, projectId, processRecordId, event.getIterationNo(), new GenericPlateType(v.getLabwareType()));
                        workEventPusher.sendStartInstancePlateEvent(event1);
                    });

        });

        // 通量消费完成
        processIterationManager.finishedIteration(processRecordId.getLongId(), event.getIterationNo());
    }

}
