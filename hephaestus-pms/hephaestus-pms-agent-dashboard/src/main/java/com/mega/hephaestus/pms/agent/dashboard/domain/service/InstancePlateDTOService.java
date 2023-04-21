package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.component.commons.date.DateUtil;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.InstanceLabwareDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProcessRecordDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstancePlateDynamicDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstancePlateNoCountDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.PlateCountDTO;
import com.mega.hephaestus.pms.data.model.entity.ProcessEntity;
import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;
import com.mega.hephaestus.pms.data.model.service.IProcessService;
import com.mega.hephaestus.pms.data.model.service.ILabwarePlateTypeService;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.data.mysql.service.IProcessLabwareService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yinyse
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstancePlateDTOService {


    private final ProcessRecordDTOManager processRecordDTOManager;

    private final IInstanceTaskService instanceTaskService;

    private final InstanceLabwareDTOManager instanceLabwareDTOManager;

    private final ILabwarePlateTypeService plateTypeService;

    private final IProcessLabwareService processLabwareService;

    private final IProcessService groupService;
//

    /**
     * 板动态 根据板类型分组
     *
     * @return 板动态
     */
    public Map<String, List<InstancePlateDynamicDTO>> labwareDynnamic() {

        // 获取正在运行的实验组
        Optional<ProcessRecordEntity> runningGroupOptional = processRecordDTOManager.getLast();
        List<InstanceLabwareModel> list = new ArrayList<>();
        if (runningGroupOptional.isPresent()) {
            // 查询正在运行的实验组的板池情况
            list = instanceLabwareDTOManager.getAllInstanceLabware(runningGroupOptional.get().getId());
        }

        if (CollectionUtils.isNotEmpty(list)) {

            ProcessRecordEntity processRecordEntity = runningGroupOptional.get();
            Long processId = processRecordEntity.getProcessId();
            // 流程耗材
            List<ProcessLabwareEntity> processLabwareEntities = processLabwareService.listByProcessId(processId);
            // 流程
            ProcessEntity group = groupService.getById(processId);

            List<String> labwareTypes = processLabwareEntities.stream().sorted(Comparator.comparing(ProcessLabwareEntity::getSortOrder)).map(ProcessLabwareEntity::getLabwareType).distinct().collect(Collectors.toList());

            // 根据项目id 拿到耗材
            List<LabwarePlateTypeEntity> labwarePlateTypeEntities = plateTypeService.listByResourceGroupId(group.getProjectId());

            Map<String, List<LabwarePlateTypeEntity>> plateTypeMap = labwarePlateTypeEntities.stream().collect(Collectors.groupingBy(LabwarePlateTypeEntity::getPlateKey));

            return list.stream().map(instancePlate -> {
                InstancePlateDynamicDTO plateDynamicDTO = new InstancePlateDynamicDTO();

                plateDynamicDTO.setId(instancePlate.getId());
                String labwareType = instancePlate.getLabwareType();
                if (plateTypeMap.containsKey(labwareType)) {
                    List<LabwarePlateTypeEntity> labwarePlateTypeEntityList = plateTypeMap.get(labwareType);
                    if (CollectionUtils.isNotEmpty(labwarePlateTypeEntityList)) {
                        plateDynamicDTO.setTransientExperimentPoolTypeName(labwarePlateTypeEntityList.get(0).getPlateName());
                    }
                }
//
                if (instancePlate.isFinished()) {

                    plateDynamicDTO.setTransientStatus(3);
                    plateDynamicDTO.setTransientTime(instancePlate.getFinishTime());

                } else {
                    if (instancePlate.isConsumed()) {
                        plateDynamicDTO.setTransientStatus(2);
                        plateDynamicDTO.setTransientTime(instancePlate.getConsumeTime());
                    } else {
                        plateDynamicDTO.setTransientStatus(1);
                        plateDynamicDTO.setTransientTime(null);
                    }

                }
                plateDynamicDTO.setExperimentPoolType(instancePlate.getLabwareType());
                plateDynamicDTO.setPlateNo(instancePlate.getIterationNo());
                plateDynamicDTO.setCreateTime(new Date());
                plateDynamicDTO.setInstanceId(instancePlate.getInstanceId());
                return plateDynamicDTO;
            }).sorted(Comparator.comparing(InstancePlateDynamicDTO::getPlateNo).thenComparing(InstancePlateDynamicDTO::getCreateTime)).collect(Collectors.groupingBy(InstancePlateDynamicDTO::getExperimentPoolType, () -> new TreeMap<>(Comparator.comparing(o -> labwareTypes.indexOf(o))), Collectors.toList()));
        }
        return Map.of();

    }


    /**
     * 统计正在运行中的板信息，对总数、完成数、消费数的统计
     *
     * @return
     */
    public PlateCountDTO countRunningConsumables() {
        Optional<ProcessRecordEntity> runningGroupOptional = processRecordDTOManager.getLast();
        if (runningGroupOptional.isPresent()) {
            List<InstanceLabwareModel> list = instanceLabwareDTOManager.getAllInstanceLabware(runningGroupOptional.get().getId());
            PlateCountDTO dto = new PlateCountDTO();

            long total = list.size();
            dto.setTotal(total);

            long consumedCount = list.stream().filter(InstanceLabwareModel::isConsumed).count();
            dto.setConsumedTotal(consumedCount);

            long finishedCount = list.stream().filter(InstanceLabwareModel::isFinished).count();
            dto.setFinishedTotal(finishedCount);


            List<InstanceTaskEntity> taskList = instanceTaskService.listByHistoryId(runningGroupOptional.get().getId());
            // 最晚结束的
            Optional<InstanceTaskEntity> max = taskList.stream().filter(v -> Objects.nonNull(v.getEndTime())).max(Comparator.comparing(InstanceTaskEntity::getEndTime));

            // 最早开始
            Optional<InstanceTaskEntity> min = taskList.stream().filter(v -> Objects.nonNull(v.getStartTime())).min(Comparator.comparing(InstanceTaskEntity::getStartTime));

            Date startTime, endTime;

            if (min.isPresent()) {
                startTime = min.get().getStartTime();
            } else {
                startTime = new Date();
            }

            // 实验未完成 结束时间应该等于当前时间
            if (total != finishedCount) {
                endTime = new Date();
            } else {
                if (max.isPresent()) {
                    endTime = max.get().getEndTime();
                } else {
                    endTime = new Date();
                }
            }

            dto.setStartTime(startTime);
            dto.setEndTime(endTime);
            dto.setStartTimeFormat(DateUtil.toString(startTime, "HH:mm:ss"));
            dto.setEndTimeFormat(DateUtil.toString(endTime, "HH:mm:ss"));

            long durationTime = DateUtil.getIntervalTime(startTime, endTime);
            dto.setDurationTimeFormat(DateUtil.formatDuration(durationTime));

            return dto;
        }

        return null;
    }

    // 通量情况统计
    public List<InstancePlateNoCountDTO> iterationMetrics() {
        // 获取正在运行的实验组
        Optional<ProcessRecordEntity> runningGroupOptional = processRecordDTOManager.getLast();
        List<InstancePlateNoCountDTO> result = new ArrayList<>();
        if (runningGroupOptional.isPresent()) {

            List<InstanceLabwareModel> allInstanceLabware = instanceLabwareDTOManager.getAllInstanceLabware(runningGroupOptional.get().getId());

            // 按照通量号来分组
            Map<Integer, List<InstanceLabwareModel>> collect = allInstanceLabware.stream().collect(Collectors.groupingBy(InstanceLabwareModel::getIterationNo));

            collect.forEach((iterationNo, labwareList) -> {
                // 总数
                int total = labwareList.size();
                //
                List<InstanceLabwareModel> finishedLabwareList = labwareList.stream().filter(v -> v.isFinished()).collect(Collectors.toList());
                int finishedTotal = finishedLabwareList.size();
                InstancePlateNoCountDTO dto = new InstancePlateNoCountDTO();
                dto.setPlateNo(String.valueOf(iterationNo));
                dto.setTotal(total);
                double completionRateDouble = (double) finishedTotal / (double) total;
                double completionRate = Double.parseDouble(new DecimalFormat("#0.00").format(completionRateDouble * 100));
                dto.setCompletionRate(completionRate);
                dto.setFinishedTotal(finishedTotal);
                result.add(dto);
            });

            return result.stream().sorted(Comparator.comparing(InstancePlateNoCountDTO::getPlateNo, Comparator.comparingInt(Integer::parseInt))).collect(Collectors.toList());

        }
        return result;
    }
}
