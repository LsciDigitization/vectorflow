package com.mega.hephaestus.pms.workflow.testutils;

import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceStepService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 胡贤明
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ExperimentGroupHistoryUtils {


    private final IInstanceTaskService instanceTaskService;

    private final IInstanceStepService stepService;

    /**
     * 批量更新批次号
     *
     * @param experimentGroupHistoryId 实验组历史表 主键id
     */
    public void updateBatchNo(long experimentGroupHistoryId) {

        //  获取等于STEP12步骤
        List<InstanceTaskEntity> list = instanceTaskService.lambdaQuery()
                .eq(InstanceTaskEntity::getStepKey, StepTypeEnum.STEP12.getCode())
//                .eq(InstanceTaskEntity::getExperimentGroupHistoryId,experimentGroupHistoryId)
                .list();

        Map<String, List<InstanceTaskEntity>> collect1 = list.stream().collect(Collectors.groupingBy(InstanceTaskEntity::getTaskRequestId));
        // TaskRequestId 去重
        AtomicInteger i = new AtomicInteger(1);


        Map<String, List<InstanceStepEntity>> collect2 = new TreeMap<>();

        collect1.forEach((k, v) -> {
            List<Long> longs = v.stream().map(InstanceTaskEntity::getInstanceId).collect(Collectors.toList());
            List<InstanceStepEntity> list1 = stepService.lambdaQuery().in(InstanceStepEntity::getInstanceId, longs).orderByAsc(InstanceStepEntity::getCreateTime).list();
            collect2.put(k, list1);
        });

        Collection<List<InstanceStepEntity>> collect3 = collect2.values();
        List<List<InstanceStepEntity>> collect4 = collect3.stream().sorted(new Comparator<List<InstanceStepEntity>>() {
            @Override
            public int compare(List<InstanceStepEntity> o1, List<InstanceStepEntity> o2) {

                InstanceStepEntity instanceStepEntity1 = o1.get(0);
                InstanceStepEntity instanceStepEntity2 = o2.get(0);
                if (Objects.nonNull(instanceStepEntity1) && Objects.nonNull(instanceStepEntity2)) {

                    return instanceStepEntity1.getCreateTime().compareTo(instanceStepEntity2.getCreateTime());
                }

                return 0;
            }
        }).collect(Collectors.toList());

        Set<Long> instanceIds = new HashSet<>();

        collect4.forEach(v -> {


            v.forEach(instanceStep -> {
                Long instanceId = instanceStep.getInstanceId();
                instanceIds.add(instanceId);

                InstanceStepEntity one = new InstanceStepEntity();
                one.setId(instanceStep.getId());
                one.setBatchNo(i.get());

                stepService.updateById(one);
            });
            i.incrementAndGet();
        });

        // step6

        List<InstanceTaskEntity> step6Task = instanceTaskService.lambdaQuery()
                .eq(InstanceTaskEntity::getStepKey, StepTypeEnum.STEP6.getCode())
                .in(InstanceTaskEntity::getInstanceId, instanceIds)
                .list();
        // 去重requestId
        List<String> requestIds = step6Task.stream().map(InstanceTaskEntity::getTaskRequestId).distinct().collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(requestIds)) {
            // in requestId 查询
            List<InstanceTaskEntity> listByRequestIds = instanceTaskService.lambdaQuery()
                    .in(InstanceTaskEntity::getTaskRequestId, requestIds)
                    .list();

            // 按照requestId 进行分组
            Map<String, List<InstanceTaskEntity>> collect = listByRequestIds.stream().collect(Collectors.groupingBy(InstanceTaskEntity::getTaskRequestId));

            if (!collect.isEmpty()) {
                collect.forEach((k, v) -> {
                    // 获取一个同一个requestId下的所有instanceId 即为一批
                    List<Long> instanceIds2 = v.stream().map(InstanceTaskEntity::getInstanceId).collect(Collectors.toList());

                    // 查询这一批instanceId 在step表数据
                    List<InstanceStepEntity> steps = stepService.lambdaQuery().in(InstanceStepEntity::getInstanceId, instanceIds2).list();
                    if (CollectionUtils.isNotEmpty(steps)) {
                        // 找到最大的批次号 即在Step12 刷进去的BatchNo
                        OptionalInt batchNo = steps.stream().filter(step -> Objects.nonNull(step.getBatchNo())).mapToInt(InstanceStepEntity::getBatchNo).distinct().max();
                        if (batchNo.isPresent()) {
                            // 过滤为空的 即Step6 的实例
                            // 将同一批次
                            steps.stream().filter(step -> Objects.isNull(step.getBatchNo())).forEach(step -> {
                                InstanceStepEntity step1 = new InstanceStepEntity();
                                step1.setId(step.getId());
                                step1.setBatchNo(batchNo.getAsInt());
                                stepService.updateById(step1);
                            });
                        }
                    }

                });

            }
        }

    }


}
