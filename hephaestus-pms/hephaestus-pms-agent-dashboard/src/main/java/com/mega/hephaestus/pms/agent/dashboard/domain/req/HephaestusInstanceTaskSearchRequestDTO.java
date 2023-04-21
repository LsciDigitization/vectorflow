package com.mega.hephaestus.pms.agent.dashboard.domain.req;

import com.mega.component.mybatis.common.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author yinyse
 * @description TODO 实例任务查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("instanceTaskSearchRequestDTO")
public class HephaestusInstanceTaskSearchRequestDTO  extends BasePageDTO {
}
