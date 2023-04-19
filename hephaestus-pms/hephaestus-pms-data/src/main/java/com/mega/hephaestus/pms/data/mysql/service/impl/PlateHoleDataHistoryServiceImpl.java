package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.PlateHoleDataEntity;
import com.mega.hephaestus.pms.data.mysql.entity.PlateHoleDataHistoryEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.PlateHoleDataHistoryMapper;
import com.mega.hephaestus.pms.data.mysql.mapper.PlateHoleDataMapper;
import com.mega.hephaestus.pms.data.mysql.service.IPlateHoleDataHistoryService;
import com.mega.hephaestus.pms.data.mysql.service.IPlateHoleDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 孔位数据
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class PlateHoleDataHistoryServiceImpl extends
        ServiceImpl<PlateHoleDataHistoryMapper, PlateHoleDataHistoryEntity> implements IPlateHoleDataHistoryService {

    /**
     * 根据 孔位数据id 查询历史
     *
     * @param holeDataId 孔位数据表id
     * @return 孔位历史数据
     */
    @Override
    public List<PlateHoleDataHistoryEntity> listByHoleDataId(long holeDataId) {
        List<PlateHoleDataHistoryEntity> list = lambdaQuery().eq(PlateHoleDataHistoryEntity::getHoleDataId, holeDataId).list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }
}
