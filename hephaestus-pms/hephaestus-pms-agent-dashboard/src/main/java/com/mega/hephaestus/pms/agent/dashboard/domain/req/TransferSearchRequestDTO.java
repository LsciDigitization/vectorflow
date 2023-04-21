package com.mega.hephaestus.pms.agent.dashboard.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *
 * @author xianming.hu
 */
@Data
@ApiModel("移液记录列表DTO")
public class TransferSearchRequestDTO {

    @ApiModelProperty("当前页，默认第1页")
    private Integer pageNum = 1;
    /**
     * 每页显示条数，默认10条
     */
    @ApiModelProperty("每页显示条数，默认10条")
    private Integer pageSize = 10;


}
