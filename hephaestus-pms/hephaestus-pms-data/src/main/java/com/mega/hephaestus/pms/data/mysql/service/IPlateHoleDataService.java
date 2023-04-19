package com.mega.hephaestus.pms.data.mysql.service;

import java.util.List;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.PlateHoleDataEntity;

/**
 * 孔位数据
 *
 * @author xianming.hu
 */
public interface IPlateHoleDataService extends IBaseService<PlateHoleDataEntity> {

    /**
     * 根据板id 获取孔位数据列表
     * @param plateId 板id
     * @return 孔位数据
     */
    List<PlateHoleDataEntity> listByPlateId(long plateId);

}

