package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.workflow.event.InstanceFinishedEvent;
import com.mega.hephaestus.pms.workflow.manager.dynamic.*;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentInstanceNewManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InstanceFinishedListener {

    @Autowired
    private ExperimentInstanceNewManager experimentInstanceNewManager;

    @Autowired
//    private InstancePlateManager plateManager;
    private InstanceLabwareManager instanceLabwareManager;
    @Autowired
    private InstanceStepManager stepManager;

    @Autowired
    private ExperimentGroupManager groupManager;

    @Autowired
    private ExperimentGroupHistoryManager historyGroupManager;

    @Autowired
    private InstanceTaskManager instanceTaskManager;

    @EventListener
    public void saveFinishedStatus(InstanceFinishedEvent event) {
        System.out.println("InstanceFinishedEvent=========");
        System.out.println(event.toString());

        // 保存数据库
        experimentInstanceNewManager.endInstance(event.getInstanceId());

        // 修改板子为完成
        instanceLabwareManager.finishedPlate(event.getInstanceId());

        // 修改step完成时间
        stepManager.finishedInstance(event.getInstanceId());

        // 获取实例
        try{
            Optional<InstanceEntity> instanceOptional = experimentInstanceNewManager.getInstance(event.getInstanceId());
            if(instanceOptional.isPresent()){
                InstanceEntity instance = instanceOptional.get();
                // 获取实验组id
//                Long experimentGroupId = instance.getExperimentGroupId();
                Long experimentGroupHistoryId = instance.getProcessRecordId();

                if(Objects.nonNull(experimentGroupHistoryId)){
                    //根据实验组id 获取板子
                    List<InstanceLabwareModel> plateList = instanceLabwareManager.getInstanceLabwareByProcessRecordId(experimentGroupHistoryId);
                    // 获取到了板子
                    if(CollectionUtils.isNotEmpty(plateList)){
                        // 排除掉已完成的板子 如果数量 =0 则所有都是完成的
                        log.info("InstanceFinishedEvent=========,实验组历史id:{},板子数量:{}",experimentGroupHistoryId,plateList.size());
                        List<InstanceLabwareModel> unfinishedPlate = plateList.stream()
                                .filter(plate -> !plate.isFinished())
                                .collect(Collectors.toList());
                        log.info("InstanceFinishedEvent=========,实验组历史id:{},板子数量:{},未完成板子数量:{}",experimentGroupHistoryId,plateList.size(),unfinishedPlate.size());

                        if(unfinishedPlate.size() == 0){
                            log.info("实验组历史id:{},所有板子都消费完了，共计:{}",experimentGroupHistoryId,plateList.size());

                            log.info("开始刷批次号");
                            // todo 刷批次号
                       //     instanceTaskManager.updateBatchNo(experimentGroupHistoryId);

                            // 表示所有板子都完成了 需要去修改实验组、实验组历史表的状态为完成
                            List<Long> collect = plateList.stream()
                                    .map(InstanceLabwareModel::getProcessId).distinct().collect(Collectors.toList());
                            if(CollectionUtils.isNotEmpty(collect)){
                                log.info("开始修改实验组状态");
                                groupManager.finishGroup(collect.get(0));
                            }

                            log.info("开始修改实验组历史状态");
                            historyGroupManager.finishHistoryGroup(experimentGroupHistoryId);
                        }
                    }
                }

            }
        }catch (Exception e){
            log.error("处理实验组状态异常，实例id为:{}",event.getInstanceId());

        }

    }

}
