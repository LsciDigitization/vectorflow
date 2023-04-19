package com.mega.hephaestus.pms.data.runtime.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.enums.XcapStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;

import java.util.List;


/**
 * 开盖器 service
 *
 * @author xianming.hu
 */
public interface IInstanceCapService extends IService<InstanceCapEntity> {

    /**
     * 根据状态和 实验记录id查询
     * @param xcapStatusEnum 状态
     * @param processRecordId 实验记录id
     * @return 开关盖集合
     */
    List<InstanceCapEntity> listByStatusAndExperimentGroupHistoryId(XcapStatusEnum xcapStatusEnum, long processRecordId);

}

