package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.model.enums.ExperimentGroupStatusEnum;
import com.mega.hephaestus.pms.data.model.service.*;
import com.mega.hephaestus.pms.data.mysql.entity.*;
import com.mega.hephaestus.pms.data.mysql.service.*;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceIterationConsumeEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceLabwareConsumeEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceIterationConsumeService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceLabwareConsumeService;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 */
@Component
@Slf4j
public class ExperimentGroupManagerImpl implements ExperimentGroupManager {

    @Autowired
    private IProcessService groupService;
    @Autowired
    private IHephaestusExperimentGroupPoolService groupPoolService;
    @Autowired
    private ILabwarePlateTypeService resourcePlateTypeService;
    @Autowired
    private IProcessRecordService groupHistoryService;
    @Autowired
    private IExperimentService experimentService;
    @Autowired
    private ILabwarePlateService instanceLabwareService;

    @Autowired
    private IHephaestusStorageService storageService;
    @Autowired
    private IInstanceLabwareConsumeService labwareConsumeService;

    @Autowired
    private IProcessIterationService processIterationService;
    @Autowired
    private IInstanceIterationConsumeService iterationConsumeService;
    @Autowired
    private IProcessLabwareService processLabwareService;

    // 当前应用名
    @Value("${spring.application.name}")
    private String applicationName;
    @Override
    public boolean start(long id) {
        // 查询是否有正在运行的
        ProcessEntity experimentGroup = groupService.getById(id);


        // 查询实验组关系表
        List<HephaestusExperimentGroupPool> groupPoolList = groupPoolService.listByExperimentGroupId(id);
        if (CollectionUtils.isNotEmpty(groupPoolList)) {
            AtomicInteger sendSize = new AtomicInteger();
            // todo 需要优化
            Long resourceGroupId = experimentGroup.getProjectId();
            List<LabwarePlateTypeEntity> labwarePlateTypeEntities = resourcePlateTypeService
                    .listByResourceGroupId(resourceGroupId);


            ConcurrentHashMap<String, Integer> poolTypePlateNo = new ConcurrentHashMap<>();
            labwarePlateTypeEntities.forEach(v -> {
                poolTypePlateNo.put(v.getPlateKey(), 1);
            });

            // 插入实验组历史表
            ProcessRecordEntity groupHistory = makeHephaestusExperimentGroupHistory(id, experimentGroup.getProcessName(), ExperimentGroupStatusEnum.RUNNING.getValue());
            groupHistoryService.save(groupHistory);
            // 获取实验ids
            List<Long> experimentIds = groupPoolList.stream().map(HephaestusExperimentGroupPool::getExperimentId).collect(Collectors.toList());

            // 实验{id:experimentName} map
            Map<Long, String> experimentNameMap = experimentService.listByIds(experimentIds).stream().collect(Collectors.toMap(ExperimentEntity::getId, ExperimentEntity::getExperimentName));

            // 需要插入的数据集合
            List<LabwarePlateEntity> instanceLabwares = new ArrayList<>();

            groupPoolList.forEach(pool -> {
                // 实验id
                Long experimentId = pool.getExperimentId();
                // 实验名称
                String experimentName = Objects.requireNonNullElse(experimentNameMap.get(experimentId), "");

                // 根据池id 获取板栈
                List<HephaestusResourceStorage> storageList = storageService.listByPoolIdAndLock(pool.getId());

                storageList.forEach(storage -> {
                    // 构造instancePlate对象
//                    HephaestusInstancePlate instancePlate = new HephaestusInstancePlate();
                    LabwarePlateEntity instanceLabware = new LabwarePlateEntity();
//                    instanceLabware.setExperimentId(experimentId);
//                    instanceLabware.setExperimentName(experimentName);
//                    instanceLabware.setProcessId(pool.getExperimentGroupId());
//                    instanceLabware.s(pool.getId());
//                    instanceLabware.setLabwareType(pool.getStoragePoolType());
                    instanceLabware.setLabwareNestId(storage.getId());
//                    instanceLabware.setProcessRecordId(groupHistory.getId());
//                    instanceLabware.set(pool.getDeviceKey());
//                    instanceLabware.setDeviceType(pool.getDeviceType());
//                    instanceLabware.setCreateTime(new Date());

                    Integer plateNo = poolTypePlateNo.get(pool.getStoragePoolType());
//                    instanceLabware.setIterationNo(plateNo);
                    poolTypePlateNo.put(pool.getStoragePoolType(), plateNo + 1);
                    instanceLabwares.add(instanceLabware);
                    sendSize.getAndIncrement();
                });
            });

            instanceLabwareService.saveBatch(instanceLabwares);
            log.info("发送消息数量:{}", sendSize);
            //消息发送完成 修改实验组状态
            groupService.updateExperimentGroupInitStatusById(id, ExperimentGroupStatusEnum.RUNNING.getValue());
            return true;
        }

        return false;
    }

    /**
     * 创建实验组历史对象
     *
     * @param groupId     实验组id
     * @param groupName   实验组名称
     * @param groupStatus 实验组状态
     * @return 实验组对象
     */
    private ProcessRecordEntity makeHephaestusExperimentGroupHistory(long groupId, String groupName, int groupStatus) {
        long time = (new Date()).getTime();
        String suffix = String.valueOf(time).substring(7);
        groupName = groupName + "-" + suffix;
        ProcessRecordEntity groupHistory = new ProcessRecordEntity();
        groupHistory.setProcessId(groupId);
        groupHistory.setProcessName(groupName);
        groupHistory.setProcessStatus(groupStatus);
        groupHistory.setAgentApplicationName(applicationName);
        return groupHistory;
    }


    /**
     * 根据id 修改实验组状态为完成
     *
     * @param id id
     */
    public void finishGroup(long id) {
        ProcessEntity group = groupService.getById(id);
        if (Objects.nonNull(group)) {
            log.info("修改实验组:{},状态为完成", id);
            // todo 流程完成
//            group.setExperimentGroupInitStatus(ExperimentGroupStatusEnum.FINISHED.getValue());
            groupService.updateById(group);
        }

    }

    /**
     * 根据id，运行一个实验组 新
     *
     * @param id 实验组ID
     * @return 提交结果成功与否
     * 1、创建运行记录
     * 2、创建通量消费(运行时)
     * 3、绑定通量与耗材
     * 4、创建耗材消费(运行时)
     */
    @Override
    public boolean startNew(long id) {
        // 查询是否有正在运行的
        ProcessEntity process = groupService.getById(id);

        // 获取所有的 通量
        List<ProcessIterationEntity> processIterationList = processIterationService.listByProcessId(process.getId());
        if (CollectionUtils.isNotEmpty(processIterationList)) {

            // 通量大小
            int iterateTimes = process.getIterateTimes();


            // step1 插入实验组历史表
            ProcessRecordEntity groupHistory = makeHephaestusExperimentGroupHistory(id, process.getProcessName(), ExperimentGroupStatusEnum.RUNNING.getValue());
            groupHistoryService.save(groupHistory);

            // step2 创建通量消费情况
            List<InstanceIterationConsumeEntity> collect = processIterationList.stream().map(v -> {
                InstanceIterationConsumeEntity iterationConsume = new InstanceIterationConsumeEntity();
                iterationConsume.setIterationId(v.getId());
                iterationConsume.setProcessRecordId(groupHistory.getId());
                iterationConsume.setStartTime(new Date());
                return iterationConsume;
            }).collect(Collectors.toList());

            iterationConsumeService.saveBatch(collect);

            // step3 绑定耗材和通量
            // 拿到当前流程对应的实例耗材类型
            List<ProcessLabwareEntity> processLabwareList = processLabwareService.listByProcessId(process.getId());

            // 创建实验耗材的状态
            List<InstanceLabwareConsumeEntity> labwareConsumeList = new ArrayList<>();


            processLabwareList.forEach(v ->{
                // 查询未绑定通量的耗材
                List<LabwarePlateEntity> instanceLabwareEntities = instanceLabwareService.listByProjectAndLabwareType(process.getProjectId(), v.getLabwareType());

                // 需要绑定的耗材
                List<LabwarePlateEntity> list = new ArrayList<>();


                for(int i = 0;i< iterateTimes;i++){
                    // 通量
                    ProcessIterationEntity processIterationEntity = processIterationList.get(i);

                    InstanceLabwareConsumeEntity instanceLabwareConsume = new InstanceLabwareConsumeEntity();
                    instanceLabwareConsume.setInstanceLabwareId(instanceLabwareEntities.get(i).getId());
                    instanceLabwareConsume.setProcessRecordId(groupHistory.getId());
                    instanceLabwareConsume.setIterationNo(processIterationEntity.getIterationNo());
                    instanceLabwareConsume.setProcessId(process.getId());
                    instanceLabwareConsume.setExperimentId(v.getExperimentId());
                    instanceLabwareConsume.setIterationId(processIterationEntity.getId());
                    labwareConsumeList.add(instanceLabwareConsume);



                    // labware
                    LabwarePlateEntity instanceLabwareNew = new LabwarePlateEntity();
                    instanceLabwareNew.setId(instanceLabwareEntities.get(i).getId());
//                    instanceLabwareNew.setProcessRecordId(groupHistory.getId());
//                    instanceLabwareConsume.set(v.getLabwareType());
//                    instanceLabwareNew.setProcessRecordId(groupHistory.getId());
                    list.add(instanceLabwareNew);

                }
     //           instanceLabwareService.saveOrUpdateBatch(list);

            });
            log.info("发送数量为:{}",labwareConsumeList.size());
           return labwareConsumeService.saveBatch(labwareConsumeList);

        }

        return false;
    }

}
