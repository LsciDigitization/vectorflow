package com.mega.hephaestus.pms.data.mysql.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferEntity;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Transfer表 Mapper 接口
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:38
 */
@Mapper
public interface LabwareTransferMapper extends SuperMapper<LabwareTransferEntity> {

    IPage<LabwareTransferView> listTransferJoinPlateJoinWellJoinLiquid(Page<LabwareTransferEntity> page, @Param("sampleId") Long sampleId, @Param("liquidId") Long liquidId);


    List<LabwareTransferEntity> listBySampleIdAndWellId(@Param("sampleId") Long sampleId,@Param("wellId") Long wellId,@Param("processRecordId") Long processRecordId);

}
