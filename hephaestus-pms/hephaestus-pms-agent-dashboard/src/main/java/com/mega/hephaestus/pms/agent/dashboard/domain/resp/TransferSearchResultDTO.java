package com.mega.hephaestus.pms.agent.dashboard.domain.resp;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("移液记录DTO")
public class TransferSearchResultDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 移液计划ID
     */
    private Long planId;

    /**
     * 起始板ID
     */
    private Long sourcePlate;

    /**
     * 起始板key
     */
    private String sourcePlateKey;

    /**
     * 起始孔 ID
     */
    private Long sourceWell;

    /**
     * 起始孔key
     */
    private String sourceWellKey;

    /**
     * 目标板ID
     */
    private Long destinationPlate;

    /**
     * 目标板Key
     */
    private String destinationPlateKey;
    /**
     * 目标孔 ID
     */
    private Long destinationWell;

    /**
     * 起始孔key
     */
    private String destinationWellKey;
    /**
     * 液体ID 当transfer_type为sample时 此id 为-1
     */
    private Long liquidId;

    /**
     * 液体名称
     */
    private String liquidName;
    /**
     * 样本id  transfer_type为liquid时 此id 为-1
     */
    private Long sampleId;


    /**
     * 移液类型 liquid, sample
     */
    private String transferType;

    /**
     * 移液头ID
     */
    private Long pipetteId;

    /**
     * 移液体积
     */
    private Float volume;

    /**
     * 移液时间
     */
    private Date transferTime;


    /**
     * 移液描述
     *
     * 在这次移液操作中，将7µL的上清液体（ID 3003）加入到目标板（ID 1003）的孔位（ID 2003）。
     * 移液操作使用了移液头（ID 4001），并在2023年4月10日10:40:00完成。这次移液操作处理的是液体
     */
    private String transferDescription;
}
