package com.mega.hephaestus.pms.data.mysql.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePlateEntity;

import java.util.List;



/**
 * 实例耗材
 *
 * @author xianming.hu
 */
public interface ILabwarePlateService extends IBaseService<LabwarePlateEntity> {





    /**
     *  根据项目id 和 类型获取耗材列表
     * @param projectId 项目id
     * @param labwareType 耗材类型
     * @return 耗材
     */
    List<LabwarePlateEntity> listByProjectAndLabwareType(long projectId, String labwareType);
}

