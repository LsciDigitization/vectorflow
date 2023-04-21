package com.mega.hephaestus.pms.agent.dashboard.domain.resp;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("移液记录DTO")
public class PipetteSearchResultDTO {

    /**
     * 主键
     */
    private Long id;


    /**
     * 移液头名称
     */
    private String name;

    /**
     * 移液头描述
     */
    private String description;

    /**
     * 最小容积
     */
    private Float minVolume;

    /**
     * 最大容积
     */
    private Float maxVolume;


}
