package com.mega.hephaestus.pms.data.mysql.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferEntity;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferView;

import java.util.List;


/**
 * Transfer service interface
 */
public interface ILabwareTransferService extends IBaseService<LabwareTransferEntity> {


    /**
     * 根据液体id 查询移液记录
     * @param liquidId 液体id
     * @return 移液记录
     */
    List<LabwareTransferEntity> listByLiquidId(long liquidId);


    /**
     * 根据样本id 查询移液记录
     * @param sampleId 样本id
     * @return 移液记录
     */
    List<LabwareTransferEntity> listBySampleId(long sampleId);

    IPage<LabwareTransferView> listBySampleIdAndLiquidId(Page<LabwareTransferEntity> page, long sampleId, long liquidId);

    /**
     *
     * @param sampleId
     * @param wellId
     * @return
     */
    List<LabwareTransferEntity> listBySampleIdAndWellId(long sampleId,long wellId, long processRecordId);
}

