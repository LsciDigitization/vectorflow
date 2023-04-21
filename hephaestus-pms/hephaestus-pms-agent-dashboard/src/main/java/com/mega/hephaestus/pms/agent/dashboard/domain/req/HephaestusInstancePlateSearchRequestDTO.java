package com.mega.hephaestus.pms.agent.dashboard.domain.req;

import com.mega.component.mybatis.common.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author yinyse
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("instancePlateSearchRequestDTO")
public class HephaestusInstancePlateSearchRequestDTO   extends BasePageDTO {
}
