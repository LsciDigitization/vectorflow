package com.mega.hephaestus.pms.agent.dashboard.domain.manager;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.InstanceIterationModel;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceIterationConsumeEntity;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessIterationEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceIterationConsumeService;
import com.mega.hephaestus.pms.data.mysql.service.IProcessIterationService;
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
public class InstanceIterationDTOManager {

    // 通量的消费情况
    private final IInstanceIterationConsumeService iterationConsumeService;

    // 通量情况
    private final IProcessIterationService processIterationService;

    //  所有的通量
    private static List<ProcessIterationEntity> processIterationEntityList = new CopyOnWriteArrayList<>();



    public Optional<InstanceIterationModel> getById(long id, long processRecordId){
        List<InstanceIterationModel> allInstanceIterationModel = getAllInstanceIterationModel(processRecordId);

        return allInstanceIterationModel.stream().filter(v ->v.getId() == id).findFirst();
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

    public List<InstanceIterationModel> getAllInstanceIterationModel(long processRecordId) {
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

        return  processIterationEntityList.stream().map(processIteration -> {
                    Long processIterationId = processIteration.getId();

                    // 将运行时的状态映射到 耗材中去
                    if (collect.containsKey(processIterationId)) {
                        List<InstanceIterationConsumeEntity> iterationConsumeEntities = collect.get(processIterationId);
                        if (CollectionUtils.isNotEmpty(iterationConsumeEntities)) {
                            InstanceIterationModel model = new InstanceIterationModel();
                            // 基础数据
                            model.setId(processIterationId);
                            model.setProjectId(processIteration.getProjectId());
                            model.setProcessId(processIteration.getProcessId());
                            model.setIterationNo(processIteration.getIterationNo());

                            // 消费情况
                            InstanceIterationConsumeEntity iterationConsume = iterationConsumeEntities.get(0);
                            if(Objects.nonNull(iterationConsume)){
                                model.setConsumeId(iterationConsume.getId());
                                model.setInstanceId(iterationConsume.getInstanceId());
                                model.setProcessRecordId(iterationConsume.getProcessRecordId());
                                model.setIsConsumed(iterationConsume.getIsConsumed());
                                model.setConsumeTime(iterationConsume.getConsumeTime());
                                model.setIsFinished(iterationConsume.getIsFinished());
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
