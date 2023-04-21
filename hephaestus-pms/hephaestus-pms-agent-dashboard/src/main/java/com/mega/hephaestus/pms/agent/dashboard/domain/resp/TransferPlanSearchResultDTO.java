package com.mega.hephaestus.pms.agent.dashboard.domain.resp;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("移液记录DTO")
public class TransferPlanSearchResultDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 移液计划名称
     */
    private String name;

    /**
     * 移液计划描述
     */
    private String description;


    /**
     * 起始板key
     */
    private String sourcePlateType;



    /**
     * 目标板Key
     */
    private String destinationPlateType;

    /**
     * 步骤key
     */
    private String stepKey;


    /**
     * 排序
     */
    private Integer sortOrder;


    /**
     *三种移液方式：PARTIAL（部分）、WHOLE（全部）、MIX（混合）
     *
     * 有数据，使用mix 混合转移
     * 无数据，使用partial转移，且保留两个孔位一样的sample_id
     * 无数据，使用whole转移，且源孔的sample_id清空，目标孔的sample_id由源孔的转移过来
     */
    private String sampleTransferMethod;

    /**
     * 移液类型 liquid, sample
     */
    private String transferType;

    /**
     * 移液头ID
     */
    private Long pipetteId;

    /**
     * 枪头数量
     */
    private Integer pipetteCount;

    /**
     * 液体ID
     */
    private Long liquidId;

    /**
     * 液体名称
     */
    private String liquidName;
    /**
     * 移液体积
     */
    private Float volume;
    /**
     *  孔范围
     *   A string of well ranges, e.g. "A1, B1:B3, C1:C3,D1"
     */
    private String wellRange;
}
