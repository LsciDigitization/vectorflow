package com.mega.hephaestus.pms.data.mysql.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;

import java.util.List;
import java.util.Optional;


/**
 * 流程耗材关系
 *
 * @author xianming.hu
 */
public interface IProcessLabwareService extends IBaseService<ProcessLabwareEntity> {

    /**
     * 根据流程id 获取流程耗材
     * @param processId 流程id
     * @return  流程耗材
     */
    List<ProcessLabwareEntity> listByProcessId(long processId);

    /**
     * 根据实验id 拿到对应的耗材流程
     * @param experimentId 实验id
     * @param processId 流程id
     * @return 流程耗材
     */
    Optional<ProcessLabwareEntity> getByExperimentIdAndProcessId(long experimentId,long processId);

}

