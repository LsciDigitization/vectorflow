package com.mega.hephaestus.pms.data.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePlateEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwarePlateMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwarePlateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import java.util.List;



/**
 * 实例耗材
 *
 * @author xianming.hu
 */
@Slf4j
@Service
//@DS("runtime")
public class LabwarePlateServiceImpl extends
        ServiceImpl<LabwarePlateMapper, LabwarePlateEntity> implements ILabwarePlateService {




    /**
     * 根据项目id 和 类型获取耗材列表，并且 通量id 等于null
     *
     * @param projectId   项目id
     * @param labwareType 耗材类型
     * @return 耗材
     */
    @Override
    public List<LabwarePlateEntity> listByProjectAndLabwareType(long projectId, String labwareType) {
        List<LabwarePlateEntity> list = lambdaQuery()
                .eq(LabwarePlateEntity::getProjectId, projectId)
                .eq(LabwarePlateEntity::getPlateType,labwareType)
                .list();
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        return List.of();
    }
}
