package com.mega.hephaestus.pms.agent.dashboard.domain.manager;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceLabwareConsumeEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePlateEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceLabwareConsumeService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwarePlateService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
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
public class InstanceLabwareDTOManager {

    private final ILabwarePlateService instanceLabwareService;

    // 状态控制service
    private final IInstanceLabwareConsumeService labwareConsumeService;

    private final IProcessRecordService historyService;
    private static List<LabwarePlateEntity> instanceLabwareList = new CopyOnWriteArrayList<>();


    public Optional<InstanceLabwareModel> getById(long id){
        List<InstanceLabwareModel> allInstanceLabware = getAllInstanceLabware();
        return allInstanceLabware.stream().filter(v -> id == v.getId()).findFirst();
    }

    public List<InstanceLabwareModel> listByInstanceIds(List<Long> ids,long processRecordId){
        List<InstanceLabwareModel> allInstanceLabware = getAllInstanceLabware(processRecordId);
        return allInstanceLabware.stream().filter(v -> ids.contains(v.getInstanceId())).collect(Collectors.toList());
    }


    public List<InstanceLabwareModel> listByIterationIds(List<Long> iterationIds){

        List<InstanceLabwareModel> allInstanceLabware = getAllInstanceLabware();

        return allInstanceLabware.stream().filter(v ->iterationIds.contains(v.getIterationId())).collect(Collectors.toList());
    }

    public List<InstanceLabwareModel> listByIterationId(long iterationId){

        List<InstanceLabwareModel> allInstanceLabware = getAllInstanceLabware();

        return allInstanceLabware.stream().filter(v ->v.getIterationId() == iterationId).collect(Collectors.toList());
    }


    public List<InstanceLabwareModel> getAllInstanceLabware(){
        Optional<ProcessRecordEntity> last = historyService.getLast();
        if(last.isPresent()){
            return getAllInstanceLabware(last.get().getId());
        }
        return List.of();
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


    public List<InstanceLabwareModel> getAllInstanceLabware(long processRecordId) {
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

                        model.setLabwareType(v.getPlateType());
                        model.setProjectId(v.getProjectId());
                        model.setLabwareNestId(v.getLabwareNestId());
                        model.setProcessRecordId(processRecordId);

                        model.setSortOrder(v.getSortOrder());
                        if (CollectionUtils.isNotEmpty(labwareConsumeEntityList)) {
                            InstanceLabwareConsumeEntity instanceLabwareConsume = labwareConsumeEntityList.get(0);
                            if (Objects.nonNull(instanceLabwareConsume)) {

                                model.setExperimentId(instanceLabwareConsume.getExperimentId());
                                model.setExperimentName(instanceLabwareConsume.getExperimentName());
                                model.setIterationNo(instanceLabwareConsume.getIterationNo());
                                model.setProcessId(instanceLabwareConsume.getProcessId());
                                model.setIterationId(instanceLabwareConsume.getIterationId());
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
