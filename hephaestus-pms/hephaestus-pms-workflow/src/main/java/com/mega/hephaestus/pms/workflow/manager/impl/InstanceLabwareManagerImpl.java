package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceLabwareConsumeEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePlateEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceLabwareConsumeService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwarePlateService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ProcessIterationManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.model.ProcessIterationModel;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class InstanceLabwareManagerImpl implements InstanceLabwareManager {

    private final ILabwarePlateService instanceLabwareService;

    // 状态控制service
    private final IInstanceLabwareConsumeService labwareConsumeService;

    private final ProcessIterationManager processIterationManager;

    private final ExperimentGroupHistoryManager historyManager;

    //  所有的耗材
    private static List<LabwarePlateEntity> instanceLabwareList = new CopyOnWriteArrayList<>();

    /**
     * push 一个新的板池
     *
     * @param instanceLabware 板池
     */
    @Override
    public void push(InstanceLabwareModel instanceLabware) {
        LabwarePlateEntity entity = new LabwarePlateEntity();

//        entity.setExperimentId(instanceLabware.getExperimentId());
//        entity.setExperimentName(instanceLabware.getExperimentName());
        entity.setProjectId(instanceLabware.getProjectId());
//        entity.setProcessId(instanceLabware.getProcessId());
//        entity.setIterationId(instanceLabware.getIterationId());
//        entity.setLabwareType(instanceLabware.getLabwareType());
        entity.setLabwareNestId(instanceLabware.getLabwareNestId());
//        entity.setProcessRecordId(instanceLabware.getProcessRecordId());
//        entity.setStartLocation(instanceLabware.getStartLocation());
//        entity.setIterationNo(instanceLabware.getIterationNo());
        entity.setBarCode(instanceLabware.getBarCode());
        //todo  需要写插入逻辑
        instanceLabwareService.save(entity);
    }

    /**
     * 根据池类型和实验组历史id 统计未消费板池数量
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType        池类型
     * @return 数量
     */
    @Override
    public long sizeByPoolType(long processRecordId, String poolType) {
        List<InstanceLabwareModel> all = getAllInstanceLabware(processRecordId);
        return all.stream()
                .filter(v -> v.getLabwareType().equals(poolType))
                .filter(v -> !v.isConsumed())
                .count();

        //    return instanceLabwareService.countByProcessRecordIdAndLabwareTypeAndIsConsumed(processRecordId,poolType, BooleanEnum.NO);
    }

    /**
     * 根据类型获取板池总数
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType        池类型
     * @return 数量
     */
    @Override
    public long fullSizeByPoolType(long processRecordId, String poolType) {
        List<InstanceLabwareModel> all = getAllInstanceLabware(processRecordId);

        return all.stream()
                .filter(v -> v.getLabwareType().equals(poolType))
                .count();
        //   return instanceLabwareService.countByProcessRecordIdAndLabwareType(processRecordId,poolType);
    }

    /**
     * 获取一个未消费的  板池
     * <p> SELECT * FROM hephaestus_instance_plate WHERE (experiment_group_history_id = ? AND experiment_pool_type = ? AND is_consumed = ?) ORDER BY plate_no ASC limit 1
     * <p>eg: 1602201853470793730(Long), sample(String), 0(Integer)
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType        池类型
     * @return optional
     */
    @Override
    public Optional<InstanceLabwareModel> getNonConsumed(long processRecordId, String poolType) {
        List<InstanceLabwareModel> all = getAllInstanceLabware(processRecordId);
        return all.stream()
                .filter(v -> v.getLabwareType().equals(poolType))
                .filter(v -> !v.isConsumed()).min(Comparator.comparing(InstanceLabwareModel::getIterationNo));


        //  return instanceLabwareService.getByProcessRecordIdAndLabwareTypeAndIsConsumed(processRecordId, poolType, BooleanEnum.NO);
    }

    /**
     * 获取一个未消费的  板池
     * <p>SELECT * FROM hephaestus_instance_plate WHERE (experiment_group_history_id = ? AND experiment_pool_type = ? AND plate_no = ? AND is_consumed = ?) ORDER BY plate_no ASC limit 1
     * <p>eg: 1602201853470793730(Long), standard(String), 7(Integer), 0(Integer)
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @param poolType        池类型
     * @param plateNo         板序号
     * @return optional
     */
    @Override
    public Optional<InstanceLabwareModel> getNonConsumed(long processRecordId, String poolType, int plateNo) {

        List<InstanceLabwareModel> all = getAllInstanceLabware(processRecordId);

        return all.stream()
                .filter(v -> v.getLabwareType().equals(poolType))
                .filter(v -> v.getIterationNo() == plateNo)
                .filter(v -> !v.isConsumed())
                .findFirst();
        // return instanceLabwareService.getByRecordIdAndLabwareTypeAndPlateNoAndIsConsumed(processRecordId, poolType, plateNo, BooleanEnum.NO);
    }

    /**
     * 消费一个耗材
     *
     * @param id         主键iD
     * @param instanceId 实例id
     * @return 是否成功
     */
    @Override
    public boolean consumePlate(long id, long instanceId,long processRecordId) {
//        LabwarePlateEntity instanceLabware = new LabwarePlateEntity();
//        instanceLabware.setId(id);
//        instanceLabware.setInstanceId(instanceId);
//        instanceLabware.setIsConsumed(BooleanEnum.YES);
//        instanceLabware.setConsumeTime(new Date());
//        return instanceLabwareService.updateById(instanceLabware);

        Optional<InstanceLabwareConsumeEntity> instanceLabwareConsume = labwareConsumeService.getByInstanceLabwareId(id,processRecordId);


        // 数据库有记录则直接修改
        if (instanceLabwareConsume.isPresent()) {
            InstanceLabwareConsumeEntity instanceLabwareConsumeNew = new InstanceLabwareConsumeEntity();
            instanceLabwareConsumeNew.setId(instanceLabwareConsume.get().getId());
            instanceLabwareConsumeNew.setIsConsumed(BooleanEnum.YES);
            instanceLabwareConsumeNew.setConsumeTime(new Date());
            instanceLabwareConsumeNew.setInstanceId(instanceId);

            consumeIteration(id, instanceLabwareConsume.get().getProcessRecordId());
            return labwareConsumeService.updateById(instanceLabwareConsumeNew);
        } else {
            // 没有则新增一条
            Optional<ProcessRecordEntity> runningGroup = historyManager.getRunningGroup();
            if (runningGroup.isPresent()) {
                InstanceLabwareConsumeEntity instanceLabwareConsumeNew = new InstanceLabwareConsumeEntity();
                instanceLabwareConsumeNew.setInstanceLabwareId(id);
                instanceLabwareConsumeNew.setInstanceId(instanceId);
                instanceLabwareConsumeNew.setProcessRecordId(runningGroup.get().getId());
                instanceLabwareConsumeNew.setIsConsumed(BooleanEnum.YES);
                instanceLabwareConsumeNew.setConsumeTime(new Date());

                consumeIteration(id, runningGroup.get().getId());
                return labwareConsumeService.save(instanceLabwareConsumeNew);
            }
        }

        return false;
    }

    // 获取耗材对象
    private void consumeIteration(long id, long processRecordId) {


        Optional<InstanceLabwareConsumeEntity> instanceLabwareConsumeEntityOptional = labwareConsumeService.getByInstanceLabwareId(id,processRecordId);
        if (instanceLabwareConsumeEntityOptional.isPresent()) {

            Long iterationId = instanceLabwareConsumeEntityOptional.get().getIterationId();
            if (Objects.nonNull(iterationId)) {
                Optional<ProcessIterationModel> iterationModel = processIterationManager.getById(iterationId, processRecordId);
                if (iterationModel.isPresent()) {
                    ProcessIterationModel model = iterationModel.get();
                    boolean isConsumed = model.isFinished();
                    if (!isConsumed) {
                        // 未消费 则去消费
                        processIterationManager.consumeIteration(iterationId);

                    }
                }
            }
        }


    }

    /**
     * 根据板类型拿到 实验id
     *
     * @param poolType        耗材类型
     * @param processRecordId 流程记录id(原实验组历史id)
     * @return 实验id
     */
    @Override
    public long getExperimentId(String poolType, long processRecordId) {

        List<InstanceLabwareModel> all = getAllInstanceLabware(processRecordId);

        return all.stream()
                .filter(v -> v.getLabwareType().equals(poolType))
                .map(InstanceLabwareModel::getExperimentId)
                .findFirst()
                .get();

    }

    @Override
    public List<InstanceLabwareModel> listByProcessRecordIdAndLabwareType(long processRecordId, String poolType) {
        List<InstanceLabwareModel> all = getAllInstanceLabware(processRecordId);

        return all.stream()
                .filter(v -> v.getLabwareType().equals(poolType))
                .collect(Collectors.toList());
    }

    /**
     * 根据实例id 标记实例耗材为已完成
     *
     * @param instanceId 实例id
     */
    @Override
    public void finishedPlate(long instanceId) {
        Optional<InstanceLabwareConsumeEntity> labwareConsumeOptional = labwareConsumeService.getByInstanceId(instanceId);

        if (labwareConsumeOptional.isPresent()) {
            InstanceLabwareConsumeEntity instanceLabwareConsume = labwareConsumeOptional.get();

            // 修改消费状态
            InstanceLabwareConsumeEntity instanceLabwareConsumeNew = new InstanceLabwareConsumeEntity();
            instanceLabwareConsumeNew.setIsFinished(BooleanEnum.YES);
            instanceLabwareConsumeNew.setFinishTime(new Date());
            instanceLabwareConsumeNew.setId(instanceLabwareConsume.getId());
            labwareConsumeService.updateById(instanceLabwareConsumeNew);


            // step1 根据当前耗材 拿到通量的id

            if (Objects.nonNull(instanceLabwareConsume)) {




                Long iterationId = instanceLabwareConsume.getIterationId();
                if (Objects.nonNull(iterationId)) {
                    Optional<ProcessIterationModel> iterationModel = processIterationManager.getById(iterationId, instanceLabwareConsume.getProcessRecordId());
                    if (iterationModel.isPresent()) {
                        ProcessIterationModel model = iterationModel.get();
                        boolean isFinished = model.isFinished();
                        if (!isFinished) {
                            // 通量未完成
                            // step2 根据通量id 查询当前的耗材
                            List<InstanceLabwareModel> labwareEntityList = listByIterationId(iterationId, instanceLabwareConsume.getProcessRecordId());

                            //  step3 判断耗材是不是都完成 都完成 则修改 当前通量完成
                            int labwareTotal = labwareEntityList.size();
                            // 完成的数量
                            List<InstanceLabwareModel> completedList = labwareEntityList.stream()
                                    .filter(v ->v.isFinished())
                                    .collect(Collectors.toList());
                            int completedTotal = completedList.size();
                            // 两个数量相等 则都完成了
                            if (labwareTotal == completedTotal) {
                                processIterationManager.finishedIteration(iterationId);
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 根据流程记录id 获取所有耗材
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @return List<LabwarePlateEntity>
     */
    @Override
    public List<InstanceLabwareModel> getInstanceLabwareByProcessRecordId(long processRecordId) {
        return getAllInstanceLabware(processRecordId);
    }


    /**
     * 根据实例id 获取
     *
     * @param instanceId 实例id
     * @return Optional<LabwarePlateEntity>
     */
    @Override
    public Optional<InstanceLabwareModel> getByInstanceId(long instanceId) {
        Optional<InstanceLabwareConsumeEntity> labwareConsumeEntity = labwareConsumeService.getByInstanceId(instanceId);
        if (labwareConsumeEntity.isPresent()) {
            InstanceLabwareConsumeEntity instanceLabwareConsume = labwareConsumeEntity.get();
            List<InstanceLabwareModel> all = getAllInstanceLabware(instanceLabwareConsume.getProcessRecordId());

            return all.stream()
                    .filter(v -> Objects.nonNull(v.getInstanceId()))
                    .filter(v -> v.getInstanceId() == instanceId)
                    .findFirst();

        }
        return Optional.empty();
    }

    /**
     * 根据通量id 拿到耗材列表
     *
     * @param iterationId 通量id
     * @return 耗材
     */
    @Override
    public List<InstanceLabwareModel> listByIterationId(long iterationId, long processRecordId) {
        List<InstanceLabwareModel> all = getAllInstanceLabware(processRecordId);

        return all.stream()
                .filter(v -> v.getIterationId() == iterationId)
                .collect(Collectors.toList());

    }


    /**
     * 查询耗材consume 情况
     *
     * @param processRecordId 流程id
     * @return 耗材consume情况
     */
    private List<InstanceLabwareConsumeEntity> getLabwareConsumeList(long processRecordId) {

        return labwareConsumeService.listByProcessRecordId(processRecordId);
    }

    private List<InstanceLabwareModel> getAllInstanceLabware(long processRecordId) {
        // 拿到所有的耗材
        // 耗材属于编排配置 只需要去数据库拿一次就行
        if (CollectionUtils.isEmpty(instanceLabwareList)) {
            instanceLabwareList = instanceLabwareService.list();
        }

        List<InstanceLabwareConsumeEntity> labwareConsumeList = new ArrayList<>();
        try {
            labwareConsumeList = getLabwareConsumeList(processRecordId);
        } catch (Exception e) {
            log.error("获取实例耗材consume失败，{}",e);

        }
        // 实例耗材consume 按照 耗材id 分组
        Map<Long, List<InstanceLabwareConsumeEntity>> collect = labwareConsumeList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(InstanceLabwareConsumeEntity::getInstanceLabwareId));

        return instanceLabwareList.stream().map(v -> {
                    Long instanceLabwareId = v.getId();

                    // 将运行时的状态映射到 耗材中去
                    if (collect.containsKey(instanceLabwareId)) {
                        List<InstanceLabwareConsumeEntity> labwareConsumeEntityList = collect.get(instanceLabwareId);
                        InstanceLabwareModel model = new InstanceLabwareModel();
                        model.setId(v.getId());

                        model.setProjectId(v.getProjectId());

                        model.setLabwareNestId(v.getLabwareNestId());
                        model.setProcessRecordId(processRecordId);
                        model.setLabwareType(v.getPlateType());

                        model.setSortOrder(v.getSortOrder());
                        if (CollectionUtils.isNotEmpty(labwareConsumeEntityList)) {
                            InstanceLabwareConsumeEntity instanceLabwareConsume = labwareConsumeEntityList.get(0);
                            if (Objects.nonNull(instanceLabwareConsume)) {
                                model.setExperimentId(instanceLabwareConsume.getExperimentId());
                                model.setProcessId(instanceLabwareConsume.getProcessId());
                                model.setIterationId(instanceLabwareConsume.getIterationId());
                                model.setIterationNo(instanceLabwareConsume.getIterationNo());
                                // add plate_id
                                model.setPlateId(instanceLabwareConsume.getInstanceLabwareId());

                                if (Objects.nonNull(instanceLabwareConsume.getIsConsumed())) {
                                    model.setConsumed(instanceLabwareConsume.getIsConsumed().toBoolean());
                                } else {
                                    model.setConsumed(false);
                                }

                                if (Objects.nonNull(instanceLabwareConsume.getIsFinished())) {
                                    model.setFinished(instanceLabwareConsume.getIsFinished().toBoolean());
                                } else {
                                    model.setFinished(false);
                                }
                                model.setConsumeTime(instanceLabwareConsume.getConsumeTime());
                                model.setFinishTime(instanceLabwareConsume.getFinishTime());
                                model.setInstanceId(instanceLabwareConsume.getInstanceId());
                            }

                        }
                        return model;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .filter(v -> Objects.nonNull(v.getProcessRecordId()))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

    }
}
