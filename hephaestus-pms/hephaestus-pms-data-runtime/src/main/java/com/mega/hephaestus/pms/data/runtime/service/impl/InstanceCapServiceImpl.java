package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.enums.XcapStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceCapMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceCapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 胡贤明
 */
@Slf4j
@Service
@DS("runtime")
public class InstanceCapServiceImpl extends
        ServiceImpl<InstanceCapMapper, InstanceCapEntity> implements IInstanceCapService {
    /**
     * 根据状态和 实验记录id查询
     *
     * @param xcapStatusEnum           状态
     * @param processRecordId 实验记录id
     * @return 开关盖集合
     */
    @Override
    public List<InstanceCapEntity> listByStatusAndExperimentGroupHistoryId(XcapStatusEnum xcapStatusEnum, long processRecordId) {
        List<InstanceCapEntity> list = lambdaQuery()
                .eq(InstanceCapEntity::getDeviceStatus, xcapStatusEnum)
                .eq(InstanceCapEntity::getProcessRecordId, processRecordId)
                .list();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }
}
