package com.mega.hephaestus.pms.data.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceNestGroupEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.DeviceNestGroupMapper;
import com.mega.hephaestus.pms.data.mysql.service.IDeviceNestGroupService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * 设备托架组
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class DeviceNestGroupServiceImpl extends
        ServiceImpl<DeviceNestGroupMapper, DeviceNestGroupEntity> implements IDeviceNestGroupService {

    /**
     * 根据项目id 设备key 查询托架组
     *
     * @param projectId 项目id
     * @param deviceKey 设备key
     * @return 托架组
     */
    @Override
    public List<DeviceNestGroupEntity> listByProjectIdAndDeviceKey(long projectId, String deviceKey) {
        List<DeviceNestGroupEntity> list = lambdaQuery().eq(DeviceNestGroupEntity::getProjectId, projectId)
                .eq(DeviceNestGroupEntity::getDeviceKey, deviceKey)
                .eq(DeviceNestGroupEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (Objects.isNull(list)) {
            return List.of();
        }
        return list;
    }
}
