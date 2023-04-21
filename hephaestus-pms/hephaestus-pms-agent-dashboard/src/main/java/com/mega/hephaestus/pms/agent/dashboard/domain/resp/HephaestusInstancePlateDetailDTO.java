package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author yinyse
 */
@Data
@ApiModel("实例板详情")
public class HephaestusInstancePlateDetailDTO {

    @ApiModelProperty("实例id")
    private Long instanceId;

    // 设备key
    @ApiModelProperty("设备key")
    private String deviceKey;

    @ApiModelProperty("实验名称")
    private String experimentName;

    // 是否消费 0 否 1 是
    @ApiModelProperty("是否消费 0 否 1 是 ")
    private BooleanEnum isConsumed;

    // 消息ID
    @ApiModelProperty("消费时间")
    private Date consumeTime;
}
