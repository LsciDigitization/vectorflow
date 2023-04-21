package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("样品数据DTO")
public class SampleDTO {

    private Long id;

    /**
     * 样品名称
     */
    private String name;

    /**
     * 样品描述
     */
    private String description;

    /**
     * 样品类型
     */
    private String type;


    /**
     * 板id
     */
    private Long wellId;


    /**
     * 孔位总容积
     */
    private Float wellVolume;


    /**
     * 孔位当前总液体容积
     */
    private Float wellTotalVolume;


    /**
     * 样品浓度
     */
    private Float concentration;

    /**
     *附加数据
     */
    private String additionalData;
}
