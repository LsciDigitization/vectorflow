package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.InventoryLocationEntity;

import java.util.List;

/**
 *
 *
 * @author xianming.hu
 */
public interface IInventoryLocationService extends IService<InventoryLocationEntity> {


    /**
     * 根据父id 查询
     * @param parentId 父ID
     * @return 集合
     */
    List<InventoryLocationEntity> listByParentId(long parentId);
}

