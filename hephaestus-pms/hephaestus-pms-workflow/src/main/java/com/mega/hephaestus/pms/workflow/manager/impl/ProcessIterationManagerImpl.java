package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceIterationConsumeEntity;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessIterationEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceIterationConsumeService;
import com.mega.hephaestus.pms.data.mysql.service.IProcessIterationService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ProcessIterationManager;
import com.mega.hephaestus.pms.workflow.manager.model.ProcessIterationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessIterationManagerImpl implements ProcessIterationManager {

    // 通量的消费情况
    private final IInstanceIterationConsumeService iterationConsumeService;

    // 通量情况
    private final IProcessIterationService processIterationService;

    //  所有的通量
    private static List<ProcessIterationEntity> processIterationEntityList = new CopyOnWriteArrayList<>();

    private final ExperimentGroupHistoryManager processRecordManager;

    /**
     * 消费一个通量
     *
     * @param id 通量id
     * @return 是否成功
     */
    @Override
    public boolean consumeIteration(long id) {
        Optional<ProcessRecordEntity> runningGroup = processRecordManager.getRunningGroup();
        if(runningGroup.isPresent()){
            Optional<InstanceIterationConsumeEntity> iterationConsumeOptional = iterationConsumeService.getByIterationId(id,runningGroup.get().getId());
            if (iterationConsumeOptional.isPresent()) {
                InstanceIterationConsumeEntity iterationConsume = new InstanceIterationConsumeEntity();
                iterationConsume.setId(iterationConsumeOptional.get().getId());
                iterationConsume.setConsumeTime(new Date());
                iterationConsume.setIsConsumed(BooleanEnum.YES);
                return iterationConsumeService.updateById(iterationConsume);
            }
        }

        return false;
    }

    /**
     * 通量消费
     *
     * @param processRecordId 记录id
     * @param iterationNo     通量号
     * @return 是否成功消费
     */
    @Override
    public boolean consumeIteration(ProcessRecordId processRecordId, int iterationNo) {
        return consumeIteration(processRecordId.getLongId(),iterationNo);
    }

    /**
     * 通量消费
     *
     * @param processRecordId 记录id
     * @param iterationNo     通量号
     * @return 是否成功消费
     */
    @Override
    public boolean consumeIteration(long processRecordId, int iterationNo) {
        List<ProcessIterationModel> allInstanceLabware = getAllInstanceIterationModel(processRecordId);
        Optional<ProcessIterationModel> first = allInstanceLabware.stream().filter(v -> v.getIterationNo() == iterationNo).findFirst();
        if (first.isPresent()) {
            ProcessIterationModel processIterationModel = first.get();
            boolean consumed = processIterationModel.isConsumed();
            if (consumed) {
                log.error("记录id:{},通量号:{},消费失败,失败原因:【该通量已消费过,不允许重复消费】", processRecordId, iterationNo);
                return false;
            }else {
                InstanceIterationConsumeEntity iterationConsume = new InstanceIterationConsumeEntity();
                // consumeId 实际为 iterationConsume的id
                iterationConsume.setId(processIterationModel.getConsumeId());
                iterationConsume.setConsumeTime(new Date());
                iterationConsume.setIsConsumed(BooleanEnum.YES);
                return iterationConsumeService.updateById(iterationConsume);
            }
        }
        log.error("记录id:{},通量号:{},消费失败,失败原因:【该通量未找到】", processRecordId, iterationNo);
        return false;
    }

    /**
     * 完成一个通量
     *
     * @param id 通量id
     * @return 是否成功
     */
    @Override
    public boolean finishedIteration(long id) {
        Optional<ProcessRecordEntity> runningGroup = processRecordManager.getRunningGroup();
        if(runningGroup.isPresent()){
            Optional<InstanceIterationConsumeEntity> iterationConsumeOptional = iterationConsumeService.getByIterationId(id,runningGroup.get().getId());
            if (iterationConsumeOptional.isPresent()) {
                InstanceIterationConsumeEntity iterationConsume = new InstanceIterationConsumeEntity();
                iterationConsume.setId(iterationConsumeOptional.get().getId());
                iterationConsume.setFinishTime(new Date());
                iterationConsume.setIsFinished(BooleanEnum.YES);
                return iterationConsumeService.updateById(iterationConsume);
            }
        }

        return false;
    }

    /**
     * 完成当前记录的 通量
     *
     * @param processRecordId 记录id
     * @param iterationNo     通量号
     * @return 是否完成
     */
    @Override
    public boolean finishedIteration(ProcessRecordId processRecordId, int iterationNo) {
        return finishedIteration(processRecordId.getLongId(),iterationNo);
    }

    /**
     * 完成当前记录的 通量
     *
     * @param processRecordId 记录id
     * @param iterationNo     通量号
     * @return 是否完成
     */
    @Override
    public boolean finishedIteration(long processRecordId, int iterationNo) {

        List<ProcessIterationModel> allInstanceLabware = getAllInstanceIterationModel(processRecordId);
        Optional<ProcessIterationModel> first = allInstanceLabware.stream().filter(v -> v.getIterationNo() == iterationNo).findFirst();
        if (first.isPresent()) {
            ProcessIterationModel processIterationModel = first.get();
            boolean finished = processIterationModel.isFinished();
            if (finished) {
                log.error("记录id:{},通量号:{},完成失败,失败原因:【该通量已完成,不允许重复完成】", processRecordId, iterationNo);
                return false;
            }else {
                InstanceIterationConsumeEntity iterationConsume = new InstanceIterationConsumeEntity();
                // consumeId 实际为 iterationConsume的id
                iterationConsume.setId(processIterationModel.getConsumeId());
                iterationConsume.setFinishTime(new Date());
                iterationConsume.setIsFinished(BooleanEnum.YES);
                return iterationConsumeService.updateById(iterationConsume);
            }
        }
        log.error("记录id:{},通量号:{},完成失败,失败原因:【该通量未找到】", processRecordId, iterationNo);
        return false;

    }

    /**
     * 根据流程记录id 拿到未消费的通量
     *
     * @param processRecordId 记录id
     * @return 通量
     */
    @Override
    public Optional<ProcessIterationModel> getUnConsumed(ProcessRecordId processRecordId) {
        return getUnConsumed(processRecordId.getLongId());
    }

    /**
     * 根据流程记录id 拿到未消费的通量
     *
     * @param processRecordId 记录id
     * @return 通量
     */
    @Override
    public Optional<ProcessIterationModel> getUnConsumed(long processRecordId) {

        List<ProcessIterationModel> allInstanceLabware = getAllInstanceIterationModel(processRecordId);
        return allInstanceLabware.stream().filter(v -> !v.isConsumed()).findFirst();
    }

    /**
     * 根据通量号 拿到未消费的通量
     *
     * @param processRecordId 记录id
     * @param iterationNo     通量号
     * @return 通量
     */
    @Override
    public Optional<ProcessIterationModel> getUnConsumed(long processRecordId, int iterationNo) {
        List<ProcessIterationModel> allInstanceLabware = getAllInstanceIterationModel(processRecordId);
        return allInstanceLabware.stream()
                .filter(v ->!v.isConsumed())
                .filter(v -> v.getIterationNo() == iterationNo)
                .findFirst();
    }

    /**
     * 根据通量id 获取通量model
     *
     * @param id id
     * @return 通量model
     */
    @Override
    public Optional<ProcessIterationModel> getById(long id, long processRecordId) {
        List<ProcessIterationModel> allInstanceLabware = getAllInstanceIterationModel(processRecordId);
        return allInstanceLabware.stream().filter(v -> v.getId() == id).findFirst();
    }

    /**
     * 获取未消费的 通量列表
     *
     * @param processRecordId 通量
     * @return 未消费的通量列表
     */
    @Override
    public List<ProcessIterationModel> listByUnConsumed(ProcessRecordId processRecordId) {
        return listByUnConsumed(processRecordId.getLongId());
    }

    /**
     * 获取未消费的 通量列表
     *
     * @param processRecordId 通量
     * @return 未消费的通量列表
     */
    @Override
    public List<ProcessIterationModel> listByUnConsumed(long processRecordId) {
        List<ProcessIterationModel> allInstanceLabware = getAllInstanceIterationModel(processRecordId);
        if(CollectionUtils.isNotEmpty(allInstanceLabware)){
            return allInstanceLabware.stream().filter(v -> ! v.isConsumed()).collect(Collectors.toList());
        }
        return List.of();
    }

    /**
     * 查询耗材consume 情况
     *
     * @param processRecordId 流程id
     * @return 耗材consume情况
     */
    private List<InstanceIterationConsumeEntity> getIterationConsume(long processRecordId) {

        return iterationConsumeService.listByProcessRecordId(processRecordId);
    }

    private List<ProcessIterationModel> getAllInstanceIterationModel(long processRecordId) {
        // 拿到所有的耗材
        // 耗材属于编排配置 只需要去数据库拿一次就行
        if (CollectionUtils.isEmpty(processIterationEntityList)) {
            processIterationEntityList = processIterationService.list();
        }

        List<InstanceIterationConsumeEntity> labwareConsumeList = new ArrayList<>();
        try {
            labwareConsumeList = getIterationConsume(processRecordId);
        } catch (Exception e) {
            log.error("获取实例耗材consume失败");

        }
        // 实例耗材consume 按照 耗材id 分组
        Map<Long, List<InstanceIterationConsumeEntity>> collect = labwareConsumeList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(InstanceIterationConsumeEntity::getIterationId));

        return processIterationEntityList.stream().map(processIteration -> {
                    Long processIterationId = processIteration.getId();

                    // 将运行时的状态映射到 耗材中去
                    if (collect.containsKey(processIterationId)) {
                        List<InstanceIterationConsumeEntity> iterationConsumeEntities = collect.get(processIterationId);
                        if (CollectionUtils.isNotEmpty(iterationConsumeEntities)) {
                            ProcessIterationModel model = new ProcessIterationModel();
                            // 基础数据
                            model.setId(processIterationId);
                            model.setProjectId(processIteration.getProjectId());
                            model.setProcessId(processIteration.getProcessId());
                            model.setIterationNo(processIteration.getIterationNo());

                            // 消费情况
                            InstanceIterationConsumeEntity iterationConsume = iterationConsumeEntities.get(0);
                            if (Objects.nonNull(iterationConsume)) {
                                model.setConsumeId(iterationConsume.getId());
                                model.setInstanceId(iterationConsume.getInstanceId());
                                model.setProcessRecordId(iterationConsume.getProcessRecordId());
                                if (Objects.nonNull(iterationConsume.getIsConsumed())) {
                                    model.setConsumed(iterationConsume.getIsConsumed().toBoolean());
                                } else {
                                    model.setConsumed(false);
                                }

                                model.setConsumeTime(iterationConsume.getConsumeTime());
                                if (Objects.nonNull(iterationConsume.getIsFinished())) {
                                    model.setFinished(iterationConsume.getIsFinished().toBoolean());
                                } else {
                                    model.setFinished(false);
                                }
                                model.setFinishTime(iterationConsume.getFinishTime());
                            }

                            return model;
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .filter(v -> Objects.nonNull(v.getProcessRecordId()))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

}
