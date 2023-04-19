package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareWellEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareWellMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareWellService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * Well service implementation
 */
@Service
public class LabwareWellServiceImpl extends
        ServiceImpl<LabwareWellMapper, LabwareWellEntity> implements ILabwareWellService {

    /**
     * 根据板id 获取孔位数据列表
     *
     * @param plateId 板id
     * @return 孔位数据
     */
    @Override
    public List<LabwareWellEntity> listByPlateId(long plateId) {
        List<LabwareWellEntity> list = lambdaQuery().eq(LabwareWellEntity::getPlateId, plateId).list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }
}

