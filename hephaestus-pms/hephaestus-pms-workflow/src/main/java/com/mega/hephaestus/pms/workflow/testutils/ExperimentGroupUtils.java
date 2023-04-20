package com.mega.hephaestus.pms.workflow.testutils;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.model.enums.ExperimentGroupStatusEnum;
import com.mega.hephaestus.pms.data.model.enums.StorageStatusEnum;
import com.mega.hephaestus.pms.data.model.service.*;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import com.mega.hephaestus.pms.data.runtime.service.IInstancePlateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author 胡贤明
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ExperimentGroupUtils {
    private final IProcessService groupService;
    private final IHephaestusExperimentGroupPoolService groupPoolService;
    private final IHephaestusStorageService storageService;
    private final IExperimentService experimentService;
    private final IProcessRecordService groupHistoryService;
    private final IInstancePlateService instancePlateService;

    private final IDeviceService deviceService;
    /**
     *
     *修改实验组状态到预定义
     * @param experimentGroupId 实验组id
     */
    public void unInitGroup(long experimentGroupId) {
        // 修改状态= 0
        groupService.updateExperimentGroupInitStatusById(experimentGroupId, ExperimentGroupStatusEnum.PREDEFINED.getValue());
    }

    /**
     * 清空板池
     */
    public void cleanStorage() {
        HephaestusResourceStorage storage = new HephaestusResourceStorage();
        storage.setPoolId(null);
        storage.setStorageStatus(StorageStatusEnum.IDLE.getValue());
        storageService.lambdaUpdate().update(storage);
    }

    /**
     * 完整初始化
     * @param experimentGroupId 实验组id
     */
    public void initExperimentGroupComplete(long experimentGroupId){
        // 修改组状态为预定义
        unInitGroup(experimentGroupId);
        // 清楚storage
        cleanStorage();

        initExperimentGroup(experimentGroupId);
    }

    /**
     * 初始化
     * @param experimentGroupId 实验组id
     *
     * 废弃原因
     * 1、流程初始化逻辑调整
     * 2、流程没有状态
     *
     */
    @Deprecated(since = "20230322")
    public void initExperimentGroup(long experimentGroupId) {

//
//        // 初始化
//        ProcessEntity hephaestusExperimentGroup = this.groupService.getById(experimentGroupId);
//        if (Objects.nonNull(hephaestusExperimentGroup)) {
//            // 是否能初始化
//            boolean initStatus = hephaestusExperimentGroup.getExperimentGroupInitStatus() == ExperimentGroupStatusEnum.PREDEFINED.getValue();
//
//            if (initStatus) {
//                // 查询实验组池
//                List<HephaestusExperimentGroupPool> groupPoolList = groupPoolService.listByExperimentGroupId(experimentGroupId);
//                if (CollectionUtils.isNotEmpty(groupPoolList)) {
//                    // 检查是否满足初始化条件
//                    Boolean flag = checkInit(groupPoolList);
//
//                    if (!flag) {
//                        log.error("所需板子数量不够");
//                    } else {
//                        // 占用板栈
//                        groupPoolList.forEach(groupPool -> storageService.updateByDeviceKeyLimit(groupPool.getDeviceKey(), groupPool.getId(), groupPool.getStoragePoolSize()));
//                        // 修改当前实验组为已初始化
//                        groupService.updateExperimentGroupInitStatusById(experimentGroupId, ExperimentGroupStatusEnum.INIT.getValue());
//                    }
//                }
//            } else {
//                log.error("实验组不是预定义和回收板栈状态，不可以初始化");
//            }
//        } else {
//            log.error("不存在该实验组");
//        }
    }

    /**
     * 开始完整的实验组 一套流程
     * @param experimentGroupId 实验组id
     */
    public void startCompleteExperimentGroup (long experimentGroupId){
        // 修改组状态为预定义
        unInitGroup(experimentGroupId);
        // 清楚storage
        cleanStorage();
        // 初始化实验组
        initExperimentGroup(experimentGroupId);
        // 开始
        start(experimentGroupId);
    }

    /**
     * 开始实验
     * 废弃原因
     * 1、流程初始化逻辑调整
     * 2、流程没有状态
     *
     */
    @Deprecated(since = "20230322")
    public void start(long experimentGroupId){
//        ProcessEntity experimentGroup = groupService.getById(experimentGroupId);
//
//        boolean canStart = experimentGroup.getExperimentGroupInitStatus() != null
//                && experimentGroup.getExperimentGroupInitStatus() == ExperimentGroupStatusEnum.INIT.getValue();
//        if (canStart) {
//            // 查询实验组关系表
//            List<HephaestusExperimentGroupPool> groupPoolList = groupPoolService.listByExperimentGroupId(experimentGroupId);
//            if (CollectionUtils.isNotEmpty(groupPoolList)) {
//                AtomicInteger sendSize = new AtomicInteger();
//                ConcurrentHashMap<String,Integer> poolTypePlateNo = new ConcurrentHashMap<>();
//                poolTypePlateNo.put(PoolTypeEnum.EMPTY.getValue(),1);
//                poolTypePlateNo.put(PoolTypeEnum.STANDARD.getValue(),1);
//                poolTypePlateNo.put(PoolTypeEnum.SAMPLE.getValue(),1);
//                poolTypePlateNo.put(PoolTypeEnum.PIPETTE1.getValue(), 1);
//                poolTypePlateNo.put(PoolTypeEnum.PIPETTE2.getValue(), 1);
//                // 插入实验组历史表
//                ProcessRecordEntity groupHistory = makeHephaestusExperimentGroupHistory(experimentGroupId, experimentGroup.getProcessName(), ExperimentGroupStatusEnum.RUNNING.getValue());
//                groupHistoryService.save(groupHistory);
//                // 添加通道
////                workTaskMessage.addChannel(MessageTopicEnum.Default, workConsumer);
//
//                // 获取实验ids
//                List<Long> experimentIds = groupPoolList.stream().map(HephaestusExperimentGroupPool::getExperimentId).collect(Collectors.toList());
//
//                // 实验{id:experimentName} map
//                Map<Long, String> experimentNameMap = experimentService.listByIds(experimentIds).stream().collect(Collectors.toMap(ExperimentEntity::getId, ExperimentEntity::getExperimentName));
//
//                // 需要插入的数据集合
//                List<HephaestusInstancePlate> instancePlates = new ArrayList<>();
//
//                groupPoolList.forEach(pool -> {
//                    // 实验id
//                    Long experimentId = pool.getExperimentId();
//                    // 实验名称
//                    String experimentName = Objects.requireNonNullElse(experimentNameMap.get(experimentId), "");
//
//                    // 根据池id 获取板栈
//                    List<HephaestusResourceStorage> storageList = storageService.listByPoolIdAndLock(pool.getId());
//                    storageList.forEach(storage -> {
//
//                        // 构造instancePlate对象
//                        HephaestusInstancePlate instancePlate = new HephaestusInstancePlate();
//                        instancePlate.setExperimentId(experimentId);
//                        instancePlate.setExperimentName(experimentName);
//                        instancePlate.setExperimentGroupId(pool.getExperimentGroupId());
//                        instancePlate.setExperimentPoolId(pool.getId());
//                        instancePlate.setExperimentPoolType(pool.getStoragePoolType());
//                        instancePlate.setExperimentPlateStorageId(storage.getId());
//                        instancePlate.setExperimentGroupHistoryId(groupHistory.getId());
//                        instancePlate.setDeviceKey(pool.getDeviceKey());
//                        instancePlate.setDeviceType(pool.getDeviceType());
//                        instancePlate.setCreateTime(new Date());
//
//                        Integer plateNo = poolTypePlateNo.get(pool.getStoragePoolType());
//                        instancePlate.setPlateNo(plateNo);
//                        poolTypePlateNo.put(pool.getStoragePoolType(),plateNo+1);
//
//                        instancePlates.add(instancePlate);
//
//                        sendSize.getAndIncrement();
//                    });
//                });
//
//                instancePlateService.saveBatch(instancePlates);
//                //消息发送完成 修改实验组状态
//                groupService.updateExperimentGroupInitStatusById(experimentGroupId, ExperimentGroupStatusEnum.RUNNING.getValue());
//
//
//            } else {
//                log.error("未查询到实验池");
//            }
//
//        }else {
//            log.error("实验组初始化后，才能开始");
//        }

    }

    /**
     * 设置开关盖机器数量
     * @param number
     */
    public void setXcapNumber(int number){
        DeviceEntity device = new DeviceEntity();
        device.setIsDeleted(BooleanEnum.YES);
        deviceService.lambdaUpdate()
                .eq(DeviceEntity::getDeviceType,"IntelliXcap")
                .update(device);

        List<DeviceEntity> intelliXcap = deviceService.lambdaQuery().eq(DeviceEntity::getDeviceType, "IntelliXcap").list();

         List<DeviceEntity> deviceEntities = intelliXcap.subList(0, number);

        deviceEntities.forEach(v ->{

            v.setIsDeleted(BooleanEnum.NO);
            deviceService.updateById(v);
        });

    }

    protected ProcessRecordEntity makeHephaestusExperimentGroupHistory(long groupId, String groupName, int groupStatus) {
        ProcessRecordEntity groupHistory = new ProcessRecordEntity();
        groupHistory.setProcessId(groupId);
        groupHistory.setProcessName(groupName);
        groupHistory.setProcessStatus(groupStatus);
        return groupHistory;
    }


    protected Boolean checkInit(List<HephaestusExperimentGroupPool> groupPoolList) {
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        List<String> types = getGroupPoolDeviceType(groupPoolList);
        List<HephaestusResourceStorage> storages = storageService.listByDeviceKeys(types);
        Map<String, List<HephaestusResourceStorage>> storageGroupByDeviceKey = storages.stream().collect(Collectors.groupingBy(HephaestusResourceStorage::getDeviceKey));
        groupPoolList.forEach(groupPool -> {
            List<HephaestusResourceStorage> hephaestusStorages = storageGroupByDeviceKey.get(groupPool.getDeviceKey());
            if (CollectionUtils.isNotEmpty(hephaestusStorages)) {
                if (hephaestusStorages.size() < groupPool.getStoragePoolSize()) {
                    flag.set(false);
                }
            }

        });
        return flag.get();
    }

    protected List<String> getGroupPoolDeviceType(List<HephaestusExperimentGroupPool> groupPoolList) {
        return groupPoolList.stream().map(HephaestusExperimentGroupPool::getDeviceKey).collect(Collectors.toList());
    }
}
