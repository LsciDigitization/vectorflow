package com.mega.hephaestus.pms.data.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceNestEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.DeviceNestMapper;
import com.mega.hephaestus.pms.data.mysql.service.IDeviceNestService;
import com.mega.hephaestus.pms.data.mysql.view.NestPlateView;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 设备托架
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class DeviceNestServiceImpl extends
        ServiceImpl<DeviceNestMapper, DeviceNestEntity> implements IDeviceNestService {


    /**
     * 根据 塔组id 批量查询 托架
     *
     * @param ids 塔批量id
     * @return 托架
     */
    @Override
    public List<NestPlateView> listByNestGroupId(List<Long> ids) {
        List<NestPlateView> list = baseMapper.getNestWithPlate(ids);
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }

    /**
     * 查询设备托架
     *
     * @param projectId 项目id
     * @param deviceKey 设备key
     * @return 托架
     */
    @Override
    public List<DeviceNestEntity> listByProjectAndDeviceKey(long projectId, String deviceKey) {
        List<DeviceNestEntity> list = lambdaQuery().eq(DeviceNestEntity::getProjectId, projectId)
                .eq(DeviceNestEntity::getDeviceKey, deviceKey)
                .eq(DeviceNestEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }
}
