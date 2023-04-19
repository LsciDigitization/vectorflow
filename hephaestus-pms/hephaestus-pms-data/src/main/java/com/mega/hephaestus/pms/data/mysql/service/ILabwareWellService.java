package com.mega.hephaestus.pms.data.mysql.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareWellEntity;

import java.util.List;

public interface ILabwareWellService extends IBaseService<LabwareWellEntity> {

    /**
     * 根据板id 获取孔位数据列表
     * @param plateId 板id
     * @return 孔位数据
     */
    List<LabwareWellEntity> listByPlateId(long plateId);
}
