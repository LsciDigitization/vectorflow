package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.PlateHoleDataEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.PlateHoleDataMapper;
import com.mega.hephaestus.pms.data.mysql.service.IPlateHoleDataService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * 孔位数据
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class PlateHoleDataServiceImpl extends
        ServiceImpl<PlateHoleDataMapper, PlateHoleDataEntity> implements IPlateHoleDataService {

    /**
     * 根据板id 获取孔位数据列表
     *
     * @param plateId 板id
     * @return 孔位数据
     */
    @Override
    public List<PlateHoleDataEntity> listByPlateId(long plateId) {
        List<PlateHoleDataEntity> list = lambdaQuery().eq(PlateHoleDataEntity::getInstancePlateId, plateId).list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }
}
