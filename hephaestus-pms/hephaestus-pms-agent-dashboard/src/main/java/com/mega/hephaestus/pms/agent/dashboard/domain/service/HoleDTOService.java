package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.InstanceLabwareDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HoleDataDTO;
import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;
import com.mega.hephaestus.pms.data.model.service.ILabwarePlateTypeService;
import com.mega.hephaestus.pms.data.mysql.entity.PlateHoleDataHistoryEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareWellEntity;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareWellService;
import com.mega.hephaestus.pms.data.runtime.service.IInstancePlateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HoleDTOService {


    private final IInstancePlateService plateService;

    private final InstanceLabwareDTOManager instanceLabwareDTOManager;

    private final ILabwarePlateTypeService plateTypeService;



    private final ILabwareWellService wellService;

    /**
     * 根据板子id获取 孔数据
     *
     * @param plateId 板Id
     * @return 分页数据
     */
    public HoleDataDTO holeDataByPlateId(long plateId) {


        Optional<InstanceLabwareModel> instanceLabwareOptional = instanceLabwareDTOManager.getById(plateId);

        if (instanceLabwareOptional.isPresent()) {
            InstanceLabwareModel instanceLabware = instanceLabwareOptional.get();
            Optional<LabwarePlateTypeEntity> plateTypeOptional = plateTypeService.getByPlateKey(instanceLabware.getLabwareType(),instanceLabware.getProjectId());
            if (plateTypeOptional.isPresent()) {
                List<LabwareWellEntity> plateHoleDataList = wellService.listByPlateId(plateId);
                // 96孔板子
                LabwarePlateTypeEntity plateType = plateTypeOptional.get();
                int rows = plateType.getRows();
                int cols = plateType.getCols();
                // 结果 行的数据
                List<Map<String, LabwareWellEntity>> resultDataList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(plateHoleDataList)) {
                    Map<String, List<LabwareWellEntity>> collect = plateHoleDataList.stream().collect(Collectors.groupingBy(LabwareWellEntity::getWellKey));
                    // 行
                    char prefix;
                    int i = 0;
                    for(prefix = 'A'; prefix <= 'Z' && i< rows ; ++prefix){
                        // 列
                        Map<String, LabwareWellEntity> resultMap = new HashMap<>();
                        for (int j = 0; j < cols; j++) {
                            String holeKey = String.valueOf(prefix)+ (j+1);
                            String key = "col-" + j; // 前端列表动态key
                            if (collect.containsKey(holeKey)) {
                                LabwareWellEntity plateHoleDataEntity = collect.get(holeKey).get(0);
                                resultMap.put(key, plateHoleDataEntity);
                            }
                        }
                        resultDataList.add(resultMap);
                        i++;
                    }




                }
                List<String> keys = new ArrayList<>();

                for (int i = 0; i < cols; i++) {
                    keys.add("col-" + i);
                }
                HoleDataDTO result = new HoleDataDTO();
                result.setPlateName(plateType.getPlateName());
                result.setPlateKey(plateType.getPlateKey());
                result.setKeys(keys);
                result.setDataSources(resultDataList);
                result.setHoleRows(rows);
                result.setHoleCols(cols);
                return result;
            }
        }



        return null;
    }


    /**
     * 根据 孔位数据id 查询历史
     *
     * @param holeDataId 孔位数据Id
     */
    public List<PlateHoleDataHistoryEntity> holeDataHistoryListByHoleDataId(long holeDataId) {
//        return wellService.listByHoleDataId(holeDataId);

        return List.of();
    }
}
