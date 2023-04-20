package com.mega.hephaestus.pms.workflow.task.stageflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mega.component.bioflow.task.*;
import com.mega.hephaestus.pms.data.model.entity.ExperimentEntity;
import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.service.IExperimentService;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStageService;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStorageService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.nuc.workflow.TaskResource;
import com.mega.hephaestus.pms.workflow.event.StartWorkflowBeforeEvent;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.manager.ExperimentInstanceManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentExecutorManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentInstanceNewManager;
import com.mega.hephaestus.pms.workflow.task.taskbuild.SpecialMeta;
import com.mega.hephaestus.pms.workflow.task.taskbuild.TaskBuildManager;
import com.mega.hephaestus.pms.workflow.work.workpool.WorkThread;
import com.mega.hephaestus.pms.workflow.work.workpool.WorkThreadResource;
import com.mega.hephaestus.pms.workflow.work.worktask.WorkTaskDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 18:02
 */
@Component
public class ExperimentFlowStart {

    @Autowired
    private TaskBuildManager taskBuildManager;

    @Autowired
    private ExperimentInstanceManager experimentInstanceManager;
    @Autowired
    private ExperimentInstanceNewManager experimentInstanceNewManager;

    @Resource
    private ThreadPoolExecutor workflowExecutor;

    @Resource
    private ThreadPoolExecutor asyncExecutor;

    @Autowired
    private ExperimentExecutorManager experimentExecutorManager;

    @Autowired
    private IHephaestusStageService stageService;

    @Autowired
    private StageFlowManager stageFlowManager;

    @Autowired
    private IExperimentService experimentService;
    @Autowired
    private IHephaestusStorageService storageService;
    @Autowired
    private WorkThreadResource workThreadResource;
    @Resource
    private WorkEventPusher workEventPusher;

    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     * @param startStorageId 存储板ID
     */
    public void startWorkflow(Long experimentId, Long startStorageId) {

        Optional<ExperimentEntity> experimentOptional = experimentService.getByIdForNoDeleted(experimentId);

        experimentOptional.ifPresent(experiment -> {
            Optional<HephaestusResourceStorage> storageOptional = storageService.getByIdForNoDeleted(startStorageId);

            storageOptional.ifPresent(hephaestusStorage -> {

                // 创建实验任务
                InstanceEntity instanceEntity = experimentInstanceNewManager.newExperimentInstance(experiment, hephaestusStorage);

//                // 生成实验预排期
//                asyncExecutor.execute(() -> {
//                    computeScheduledStageTask(instanceEntity.getId());
//                });
                workEventPusher.sendStartWorkflowBeforeEvent(new StartWorkflowBeforeEvent(instanceEntity, hephaestusStorage));

                // 加入线程池执行
                workflowExecutor.execute(new WorkThread(workThreadResource, instanceEntity));
            });

        });

    }

    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     * @param workTaskDomain WorkTaskDomain
     */
    public void startWorkflow(Long experimentId, WorkTaskDomain workTaskDomain) {

        Optional<com.mega.hephaestus.pms.data.model.entity.ExperimentEntity> experimentOptional = experimentService.getByIdForNoDeleted(experimentId);

        experimentOptional.ifPresent(experiment -> {
            Optional<HephaestusResourceStorage> storageOptional = storageService.getByIdForNoDeleted(workTaskDomain.getExperimentPlateStorageId());

            storageOptional.ifPresent(hephaestusStorage -> {

                // 创建实验任务
                InstanceEntity instanceEntity = experimentInstanceNewManager.newExperimentInstance(experiment, hephaestusStorage);
                instanceEntity.setProcessRecordId(workTaskDomain.getExperimentGroupHistoryId());
                instanceEntity.setProcessId(workTaskDomain.getExperimentGroupId());
                experimentInstanceNewManager.updateInstance(instanceEntity);

//                // 生成实验预排期
//                asyncExecutor.execute(() -> {
//                    computeScheduledStageTask(instanceEntity.getId());
//                });
                workEventPusher.sendStartWorkflowBeforeEvent(new StartWorkflowBeforeEvent(instanceEntity, hephaestusStorage));

                // 加入线程池执行
                workflowExecutor.execute(new WorkThread(workThreadResource, instanceEntity));
            });

        });

    }

    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     * @param workTaskDomain WorkTaskDomain
     */
    public void startWorkflow(Long experimentId, WorkTaskDomain workTaskDomain, Consumer<InstanceEntity> consumer) {

        Optional<com.mega.hephaestus.pms.data.model.entity.ExperimentEntity> experimentOptional = experimentService.getByIdForNoDeleted(experimentId);

        experimentOptional.ifPresent(experiment -> {
            Optional<HephaestusResourceStorage> storageOptional = storageService.getByIdForNoDeleted(workTaskDomain.getExperimentPlateStorageId());

            storageOptional.ifPresent(hephaestusStorage -> {

                // 创建实验任务
                InstanceEntity instanceEntity = experimentInstanceNewManager.newExperimentInstance(experiment, hephaestusStorage);
                instanceEntity.setProcessRecordId(workTaskDomain.getExperimentGroupHistoryId());
                instanceEntity.setProcessId(workTaskDomain.getExperimentGroupId());
                experimentInstanceNewManager.updateInstance(instanceEntity);

                consumer.accept(instanceEntity);

                // 生成实验预排期
//                asyncExecutor.execute(() -> {
//                    computeScheduledStageTask(instanceEntity.getId());
//                });
                workEventPusher.sendStartWorkflowBeforeEvent(new StartWorkflowBeforeEvent(instanceEntity, hephaestusStorage));

                // 加入线程池执行
                workflowExecutor.execute(new WorkThread(workThreadResource, instanceEntity));
            });

        });

    }

    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     */
    public void startWorkflow(Long experimentId, String title, Long startStorageId) {
        try {
            // 创建实验任务
            InstanceEntity instanceEntity = experimentInstanceManager.newExperimentInstance(experimentId, title, startStorageId);

            SpecialMeta specialMeta = new SpecialMeta();
            specialMeta.putMetaData(SpecialMeta.SpecialMetaKeyEnum.EXPERIMENT_INSTANCE_ID, instanceEntity.getId().toString());

//            String stageRootTaskAsJson = experimentStageManager.getStageRootTaskAsJson(experimentId, specialMeta);
            List<HephaestusStage> hephaestusStages = stageService.listByExperimentId(experimentId);
            List<StageEntity> stageEntities = hephaestusStages.stream()
                    .map(this::toStageEntity)
                    .collect(Collectors.toList());
            com.mega.component.bioflow.task.ExperimentEntity experiment = new com.mega.component.bioflow.task.ExperimentEntity();
            experiment.setId(new ExperimentId(experimentId));
            experiment.setStages(stageEntities);
            String stageRootTaskAsJson = taskBuildManager.buildExperimentRootTaskAsJson(experiment, specialMeta);

            TaskResource taskResource = new TaskResource(stageRootTaskAsJson);

            workflowExecutor.execute(() -> stageFlowManager.start(taskResource, runId -> {
                // 启动实验状态
                experimentInstanceManager.startStage(instanceEntity.getId(), runId.getId());
            }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 指定一个阶段，开始一个实验
     *
     * @param experimentId 实验ID
     */
    public void startWorkflow(Long experimentId, String stageName, String title, Long startStorageId) {
        try {
            InstanceEntity instanceEntity = experimentInstanceManager.newExperimentInstance(experimentId, title, startStorageId);

            SpecialMeta specialMeta = new SpecialMeta();
            specialMeta.putMetaData(SpecialMeta.SpecialMetaKeyEnum.EXPERIMENT_INSTANCE_ID, instanceEntity.getId().toString());

//            String stageRootTaskAsJson = experimentStageManager.getStageRootTaskAsJson(experimentId, specialMeta);
            List<HephaestusStage> hephaestusStages = stageService.listByExperimentId(experimentId);
            List<StageEntity> stageEntities = hephaestusStages.stream()
                    .map(this::toStageEntity)
                    .collect(Collectors.toList());
            com.mega.component.bioflow.task.ExperimentEntity experiment = new com.mega.component.bioflow.task.ExperimentEntity();
            experiment.setId(new ExperimentId(experimentId));
            experiment.setStages(stageEntities);
            String stageRootTaskAsJson = taskBuildManager.buildExperimentRootTaskAsJson(experiment, specialMeta);

            TaskResource taskResource = new TaskResource(stageRootTaskAsJson);

            workflowExecutor.execute(() -> stageFlowManager.start(taskResource, stageName, runId -> {
                // TODO 获取stageId使用
                // 启动实验状态
                experimentInstanceManager.startStage(instanceEntity.getId(), runId.getId());
            }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 从头开始一个实验
     *
     * @param instanceId 实例ID
     */
    public void startWorkflowStage(Long instanceId) {
        Optional<InstanceEntity> experimentInstanceOptional = experimentInstanceManager.getExperimentInstance(instanceId);

        experimentInstanceOptional.ifPresentOrElse(hephaestusInstance -> {
            SpecialMeta specialMeta = new SpecialMeta();
            specialMeta.putMetaData(SpecialMeta.SpecialMetaKeyEnum.EXPERIMENT_INSTANCE_ID, hephaestusInstance.getId().toString());

            String stageRootTaskAsJson = null;
            try {
//                stageRootTaskAsJson = experimentStageManager.getStageRootTaskAsJson(hephaestusInstance.getExperimentId(), specialMeta);
                List<HephaestusStage> hephaestusStages = stageService.listByExperimentId(hephaestusInstance.getExperimentId());
                List<StageEntity> stageEntities = hephaestusStages.stream()
                        .map(this::toStageEntity)
                        .collect(Collectors.toList());
                com.mega.component.bioflow.task.ExperimentEntity experiment = new com.mega.component.bioflow.task.ExperimentEntity();
                experiment.setId(new ExperimentId(hephaestusInstance.getExperimentId()));
                experiment.setStages(stageEntities);
                stageRootTaskAsJson = taskBuildManager.buildExperimentRootTaskAsJson(experiment, specialMeta);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

            TaskResource taskResource = new TaskResource(stageRootTaskAsJson);

            workflowExecutor.execute(() -> stageFlowManager.start(taskResource, runId -> {
                // 启动实验状态
                experimentInstanceManager.startStage(hephaestusInstance.getId(), runId.getId());
            }));
        }, null);
    }

    /**
     * 从头开始一个实验
     *
     * @param instanceId 实例ID
     * @param stageName 阶段名称
     */
    public void startWorkflowStage(Long instanceId, String stageName) {
        Optional<InstanceEntity> experimentInstanceOptional = experimentInstanceManager.getExperimentInstance(instanceId);

        experimentInstanceOptional.ifPresentOrElse(hephaestusInstance -> {
            SpecialMeta specialMeta = new SpecialMeta();
            specialMeta.putMetaData(SpecialMeta.SpecialMetaKeyEnum.EXPERIMENT_INSTANCE_ID, hephaestusInstance.getId().toString());

            String stageRootTaskAsJson = null;
            try {
//                stageRootTaskAsJson = experimentStageManager.getStageRootTaskAsJson(hephaestusInstance.getExperimentId(), specialMeta);
                List<HephaestusStage> hephaestusStages = stageService.listByExperimentId(hephaestusInstance.getExperimentId());
                List<StageEntity> stageEntities = hephaestusStages.stream()
                        .map(this::toStageEntity)
                        .collect(Collectors.toList());
                com.mega.component.bioflow.task.ExperimentEntity experiment = new com.mega.component.bioflow.task.ExperimentEntity();
                experiment.setId(new ExperimentId(hephaestusInstance.getExperimentId()));
                experiment.setStages(stageEntities);
                stageRootTaskAsJson = taskBuildManager.buildExperimentRootTaskAsJson(experiment, specialMeta);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

            TaskResource taskResource = new TaskResource(stageRootTaskAsJson);

            workflowExecutor.execute(() -> stageFlowManager.start(taskResource, runId -> {
                // 启动实验状态
                experimentInstanceManager.startStage(hephaestusInstance.getId(), runId.getId());
            }));
        }, null);
    }

    protected StageEntity toStageEntity(HephaestusStage v) {
        StageEntity stageEntity = new StageEntity();
        stageEntity.setId(new StageId(v.getId()));
        stageEntity.setStageName(v.getStageName());
        stageEntity.setStageDescription(v.getStageDescription());
        stageEntity.setExperimentId(new ExperimentId(v.getExperimentId()));
        stageEntity.setSortOrder(v.getSortOrder());
        stageEntity.setPriorityLevel(v.getPriorityLevel());
        stageEntity.setStageType(StageType.toEnum(v.getStageType()));
        stageEntity.setIsSkip(v.getIsSkip() == 1);
        return stageEntity;
    }

}
