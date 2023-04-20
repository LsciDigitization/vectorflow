package com.mega.hephaestus.pms.workflow.manager;

//import cn.hutool.json.JSONUtil;
//import com.alibaba.fastjson2.JSONObject;
import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.model.service.*;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentInstanceStatusEnum;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentStageStatusEnum;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.workflow.instancecontext.ExperimentInstanceContextEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 实验实例操作
 */
@Component
@Deprecated(since = "20221115")
public class ExperimentInstanceManager {
    @Autowired
    private IHephaestusStageService stageService;
    @Autowired
    private IHephaestusIncubatorStorageService incubatorStorageService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IHephaestusStorageModelService storageModelService;

    // 根据实验ID，开始一个新的实验
    // instanceStatus 枚举类
    // status 枚举类
    // activeStageStatus 枚举类
    // instanceTitle 实验实例名称生成方法


    public Optional<InstanceEntity> getExperimentInstance(Long instanceId) {
        InstanceEntity instance = instanceService.getById(instanceId);
        return Optional.ofNullable(instance);
    }

    /**
     * 创建一个实验，并从第一个Stage开始执行
     *
     * @param experimentId   实验ID
     * @param instanceTitle  实验标题(用户输入)
     * @param startStorageId 开始位置ID，hephaestus_storage_model存储模型主键，所有实验必须从板栈开始。
     *                       结束位置ID，hephaestus_storage_model存储模型主键
     * @return InstanceEntity
     */
    public InstanceEntity newExperimentInstance(Long experimentId, String instanceTitle, Long startStorageId) {
        return newExperimentInstance(experimentId, instanceTitle, startStorageId, startStorageId);
    }

    /**
     * 创建一个实验，并从第一个Stage开始执行
     *
     * @param experimentId   实验ID
     * @param instanceTitle  实验标题(用户输入)
     * @param startStorageId 开始位置ID，hephaestus_storage_model存储模型主键，所有实验必须从板栈开始。
     * @param endStorageId   结束位置ID，hephaestus_storage_model存储模型主键
     * @return InstanceEntity
     */
    public InstanceEntity newExperimentInstance(Long experimentId, String instanceTitle, Long startStorageId, Long endStorageId) {
//        InstanceEntity build = null;
        // 查询数据库得到开始位置资源模型，并转换为Nuc存储模型(deviceId to nucDeviceId)
//        NucStorageModel start = storageModelService.getNucStorage(startStorageId).orElse(null);
        // 查询数据库得到结束位置资源模型，并转换为Nuc存储模型(deviceId to nucDeviceId)
//        NucStorageModel end = storageModelService.getNucStorage(endStorageId).orElse(null);



        //TODO 资源位状态校验，start，end，plateId 是否可用；start必须为板栈


        HephaestusIncubatorStorage incubatorStorage = incubatorStorageService.allocate();
        //创建实例上下文
//        Map<ExperimentInstanceContextEnum, Object> instanceContext = new EnumMap<>(ExperimentInstanceContextEnum.class);
        //设置保温箱ID
//        instanceContext.put(ExperimentInstanceContextEnum.INCUBATOR, incubatorStorage == null ? null : incubatorStorage.getId());


        InstanceEntity instanceEntity = new InstanceEntity();
        instanceEntity.setExperimentId(experimentId);
//        instanceEntity.setInstanceContext(JSONUtil.toJsonStr(instanceContext));
        instanceEntity.setInstanceTitle(instanceTitle);
        instanceEntity.setCreateTime(new Date());
        instanceEntity.setInstanceStatus(ExperimentInstanceStatusEnum.IDLE);
        instanceEntity.setActiveStageStatus(ExperimentStageStatusEnum.IDLE);
        instanceEntity.setStartStorageId(startStorageId);
        instanceEntity.setEndStorageId(endStorageId);
        instanceEntity.setCurrentStorageId(startStorageId);

        instanceService.save(instanceEntity); //持久化数据

        return instanceEntity;
    }

    /**
     * 启动实例，默认启动第一个Stage
     *
     * @param instanceId 实例ID
     * @return InstanceEntity
     */
    public InstanceEntity startStage(Long instanceId) {
        return startStage(instanceId, null);
    }

    /**
     * 启动实例，默认启动第一个Stage
     *
     * @param instanceId 实例ID
     * @param runId    WorkflowRunId
     * @return InstanceEntity
     */
    public InstanceEntity startStage(Long instanceId, String runId) {
        InstanceEntity instance = instanceService.getById(instanceId);

        InstanceEntity newInstanceEntity = new InstanceEntity();
        newInstanceEntity.setId(instance.getId());
//        Long stageId = stageService.firstStage(instance.getExperimentId()).get().getId();
        stageService.firstStage(instance.getExperimentId()).ifPresentOrElse(stage -> {
            newInstanceEntity.setActiveStageId(stage.getId());
        }, () -> {});

        newInstanceEntity.setInstanceStatus(ExperimentInstanceStatusEnum.RUNNING);
        newInstanceEntity.setActiveStageStatus(ExperimentStageStatusEnum.RUNNING);
        newInstanceEntity.setUpdateTime(new Date());
        newInstanceEntity.setInstanceStartTime(new Date());
        newInstanceEntity.setWorkflowRunId(runId);

        instanceService.updateById(newInstanceEntity);

        return newInstanceEntity;
    }

    /**
     * 从指定Stage启动实例
     *
     * @param instanceId 实例ID
     * @param stageId    StageID
     * @param runId    WorkflowRunId
     * @return 实例对象
     */
    public InstanceEntity startStage(Long instanceId, Long stageId, String runId) {
        //获取实例对象
        InstanceEntity instance = instanceService.getById(instanceId);
        InstanceEntity newInstanceEntity = new InstanceEntity();
        newInstanceEntity.setId(instance.getId());
        //设置实例状态为RUNNING
        newInstanceEntity.setInstanceStatus(ExperimentInstanceStatusEnum.RUNNING);
        //获取指定实验Stage
        HephaestusStage stage = stageService.getById(stageId);
        // stage不存在，默认开始节点
        if (stage == null) {
            stage = stageService.firstStage(instance.getExperimentId()).orElse(null);
        }
        //设置当前实验实例 stageId
        if (stage != null) {
            newInstanceEntity.setActiveStageId(stage.getId());
        }
        //设置当前实验实例 状态
        newInstanceEntity.setActiveStageStatus(ExperimentStageStatusEnum.RUNNING);
        newInstanceEntity.setUpdateTime(new Date());
        newInstanceEntity.setInstanceStartTime(new Date());
        newInstanceEntity.setWorkflowRunId(runId);
        //持久化
        instanceService.updateById(newInstanceEntity);
        return newInstanceEntity;
    }

    /**
     * 执行结束
     *
     * @param instanceId 实例ID
     * @param stageId 阶段ID
     * @return InstanceEntity
     */
    public InstanceEntity endStage(Long instanceId, Long stageId) {
        InstanceEntity instance = instanceService.getById(instanceId);
        InstanceEntity newInstanceEntity = new InstanceEntity();

        if (instance != null) {
            newInstanceEntity.setId(instance.getId());
            newInstanceEntity.setActiveStageId(stageId);
            newInstanceEntity.setActiveStageStatus(ExperimentStageStatusEnum.FINISHED);
            newInstanceEntity.setUpdateTime(new Date());
            instanceService.updateById(newInstanceEntity);
        }
        return newInstanceEntity;
    }

    /**
     * 执行失败
     *
     * @param instanceId 实例ID
     * @param stageId 阶段ID
     * @return InstanceEntity
     */
    public InstanceEntity failStage(Long instanceId, Long stageId) {
        InstanceEntity instance = instanceService.getById(instanceId);
        InstanceEntity newInstanceEntity = new InstanceEntity();
        if (instance != null) {
            newInstanceEntity.setId(instance.getId());
            newInstanceEntity.setActiveStageId(stageId);
            newInstanceEntity.setActiveStageStatus(ExperimentStageStatusEnum.FAIL);
            newInstanceEntity.setUpdateTime(new Date());
            instanceService.updateById(newInstanceEntity);
        }
        return newInstanceEntity;
    }




    /**
     * 获取指定实例中正在执行的Stage对象
     *
     * @param instanceId 实例ID
     * @return InstanceEntity
     */
    public HephaestusStage getCurrentStage(Long instanceId) {
        InstanceEntity instance = instanceService.getById(instanceId);
        if (instance != null) {
            HephaestusStage stage = stageService.getById(instance.getActiveStageId());
//            stage.setActiveStageStatus(instance.getActiveStageStatus());
            return stage;
        }
        return null;
    }

    /**
     * 通过上下文替换实例参数
     *
     * @param instance 实例对象
     * @param task     对象
     * @return
     */
    public HephaestusStageTask transTaskParameter(InstanceEntity instance, HephaestusStageTask task) {

        //获取上下文
//        Map<ExperimentInstanceContextEnum, Object> instanceContext = JSONUtil.toBean(instance.getInstanceContext(), new EnumMap(ExperimentInstanceContextEnum.class).getClass());
        // 需要替换的参数列表 replaceMap
        Map<String, Object> replaceMap = new HashMap();

        // 获取该Task默认参数
//        JSONObject taskParameter = JSONObject.parseObject(task.getTaskParameter());
//        String toDeviceId = taskParameter.getString("ToDeviceId");
//        String toNestGroupId = taskParameter.getString("ToNestGroupId");
//        Integer toNest = taskParameter.getInteger("ToNest");
        // 通过toDeviceId,toNestGroupId,toNest 查询存储对象
//        HephaestusStorageModel toNucStorageModel = this.getHephaestusStorage(toDeviceId, toNestGroupId, toNest);

//        /**
//         * 如果该Task为搬运任务，则使用上下文中的当前所在位置 context.getCurrent() 替换 参数中的 FromXXXXXXX
//         */
//        if ("RoboticArm".equals(task.getTaskCommand())) {
//            NucStorageModel nucStorageModelCurrent = getNucStorage(instance.getCurrentStorageId());
//            if(nucStorageModelCurrent!=null){
//                replaceMap.put("FromDeviceId", nucStorageModelCurrent.getDeviceId());
//                replaceMap.put("FromNestGroupId", nucStorageModelCurrent.getNestGroupId());
//                replaceMap.put("FromGripOrientation", nucStorageModelCurrent.getGripOrientation());
//                replaceMap.put("FromNest", nucStorageModelCurrent.getNest());
//            }
//
//            /**
//             *  如果该Task为搬运任务且搬运的终点为板栈，则使用上下文中的 起始位置 context.getCurrent() 替换 参数中的 ToXXXXXXX
//             */
//            // 判断是否结束位置为板栈
//            if (toNucStorageModel!=null && DeviceTypeEnum.Carousel.getValue() == toNucStorageModel.getDeviceType().getValue()) {
//                NucStorageModel nucStorageModelStart = getNucStorage(instance.getStartStorageId());
//                if(nucStorageModelStart!=null){replaceMap.put("ToDeviceId", nucStorageModelStart.getDeviceId());
//                    replaceMap.put("ToNestGroupId", nucStorageModelStart.getNestGroupId());
//                    replaceMap.put("ToGripOrientation", nucStorageModelStart.getGripOrientation());
//                    replaceMap.put("ToNest", nucStorageModelStart.getNest());}
//
//            }
//        }

//        /**
//         * 如果该Task为保温箱，则使用上下文中的Planid 替换 参数中的 plateId
//         */
//        if ("PlateIn".equals(task.getTaskCommand()) || "PlateOut".equals(task.getTaskCommand())) {
//            if (instanceContext.get(ExperimentInstanceContextEnum.INCUBATOR.value) != null) {
//                HephaestusIncubatorStorage incubatorStorage = this.incubatorStorageService.getById((Integer) instanceContext.get(ExperimentInstanceContextEnum.INCUBATOR.value));
//                if (incubatorStorage != null) {
//                    replaceMap.put("plateId", incubatorStorage.getPlateKey());
//                }
//            }
//
//        }

        // 执行参数替换
//        taskParameter = trans(taskParameter, replaceMap);
//        task.setTaskParameter(taskParameter.toString());
        //返回替换完成的Task对象
        return task;
    }

    /**
     * 完成指定Task操作，用于完成Task后更新上下文
     *
     * @param instance
     * @param task
     * @return
     */
    public InstanceEntity completeTask(InstanceEntity instance, HephaestusStageTask task) {
        //如果该操作为机械臂，则更新上下文当前位置为机械臂移动后样品的位置。
//        if ("RoboticArm".equals(task.getTaskCommand())) {
//            //得到上下文对象
//            // 得到默认参数
//            String taskParameter = task.getTaskParameter();
//            JSONObject taskParameterJson = JSONObject.parseObject(taskParameter);
//            HephaestusStorageModel current = this.getHephaestusStorage(taskParameterJson.getString("ToDeviceId"), taskParameterJson.getString("ToNestGroupId"), taskParameterJson.getInteger("ToNest"));
//
//
//            instance.setCurrentStorageId(current.getId());
//
//            InstanceEntity newInstanceEntity = new InstanceEntity();
//            newInstanceEntity.setCurrentStorageId(current.getId());
//            newInstanceEntity.setId(instance.getId());
//            instanceService.updateById(newInstanceEntity);
//        }
        return instance;
    }



    /**
     * 通过Nuc数据得到 HephaestusStorageModel
     *
     * @param nucId
     * @param nestGroupId
     * @param nest
     * @return
     */
    public HephaestusStorageModel getHephaestusStorage(String nucId, String nestGroupId, Integer nest) {
        return storageModelService.getHephaestusStorage(nucId, nestGroupId, nest).orElse(null);
    }

//    /**
//     * 通过 HephaestusStorageModel ID 获取 NucStorageModel
//     *
//     * @param hephaestusStorageModelId
//     * @return
//     */
//    @Deprecated
//    public NucStorageModel getNucStorage(Long hephaestusStorageModelId) {
//        return storageModelService.getNucStorage(hephaestusStorageModelId).orElse(null);
//    }


//    private static JSONObject trans(JSONObject jsonObj, Map<String, Object> replaceMap) {
//        for (String key : replaceMap.keySet()) {
//            jsonObj.replace(key, replaceMap.get(key));
//        }
//        return jsonObj;
//    }


}
