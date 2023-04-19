package com.mega.hephaestus.pms.data.mysql.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessIterationEntity;

import java.util.List;


/**
 * 流程通量
 *
 * @author xianming.hu
 */
public interface IProcessIterationService extends IBaseService<ProcessIterationEntity> {

    /**
     * 根据流程id 查询通量 order by iterationNo asc
     * @param processId 流程id
     * @return 通量
     */
    List<ProcessIterationEntity> listByProcessId(long processId);

}

