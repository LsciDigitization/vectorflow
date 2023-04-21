package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 开盖器定义
 *
 * @author xianming.hu
 */
@Data
@ApiModel("开盖器定义")
public class InstanceCapDTO {

    /**
     * 数据编号
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 实例ID
     */
    @ApiModelProperty("实例ID")
    private Long instanceId;


    /**
     * deviceKey
     */
    @ApiModelProperty("设备key")
    private String deviceKey;

    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private String deviceType;


    /**
     * 开盖时间
     */
    @ApiModelProperty("开盖时间")
    private Date openCapTime;


}
