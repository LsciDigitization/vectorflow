package com.mega.hephaestus.pms.data.mysql.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.PlateHoleDataHistoryEntity;

import java.util.List;


/**
 * 孔位数据
 *
 * @author xianming.hu
 */
public interface IPlateHoleDataHistoryService extends IBaseService<PlateHoleDataHistoryEntity> {


    /**
     * 根据 孔位数据id 查询历史
     * @param holeDataId 孔位数据表id
     * @return 孔位历史数据
     */
    List<PlateHoleDataHistoryEntity> listByHoleDataId(long holeDataId);

}

