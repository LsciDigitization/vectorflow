package com.mega.hephaestus.pms.data.mysql.service;

import java.util.List;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceNestGroupEntity;

/**
 * 设备托架组
 *
 * @author xianming.hu
 */
public interface IDeviceNestGroupService extends IBaseService<DeviceNestGroupEntity> {


    /**
     * 根据项目id 设备key 查询托架组
     * @param projectId 项目id
     * @param deviceKey 设备key
     * @return 托架组
     */
    List<DeviceNestGroupEntity> listByProjectIdAndDeviceKey(long projectId,String deviceKey);
}

