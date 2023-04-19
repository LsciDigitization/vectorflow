package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.InventoryLocationEntity;
import com.mega.hephaestus.pms.data.model.mapper.InventoryLocationMapper;
import com.mega.hephaestus.pms.data.model.service.IInventoryLocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author xianming.hu
 */
@Slf4j
@Service
public class InventoryLocationServiceImpl extends ServiceImpl<InventoryLocationMapper, InventoryLocationEntity> implements IInventoryLocationService {


    @Override
    public List<InventoryLocationEntity> listByParentId(long parentId) {
        List<InventoryLocationEntity> list = lambdaQuery()
                .eq(InventoryLocationEntity::getParentId, parentId)
                .eq(InventoryLocationEntity::getIsDeleted, BooleanEnum.NO)
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }
}
