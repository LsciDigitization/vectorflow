package com.mega.hephaestus.pms.data.mysql.service;


import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceNestEntity;
import com.mega.hephaestus.pms.data.mysql.view.NestPlateView;

import java.util.List;

/**
 * 设备托架
 *
 * @author xianming.hu
 */
public interface IDeviceNestService extends IBaseService<DeviceNestEntity> {


    /**
     * 根据 塔组id 批量查询 托架
     * @param ids 塔批量id
     * @return 托架
     */
    List<NestPlateView> listByNestGroupId(List<Long> ids);

    /**
     * 查询设备托架
     * @param projectId 项目id
     * @param deviceKey 设备key
     * @return 托架
     */
    List<DeviceNestEntity> listByProjectAndDeviceKey(long projectId,String deviceKey);
}

