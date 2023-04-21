package com.mega.hephaestus.pms.agent.dashboard.domain.req;




import com.mega.component.mybatis.common.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import lombok.*;


/**
 * 设备任务表
 *
 * @author xianming.hu
 * @date 2022-11-21 14:27:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("HephaestusDeviceTaskSearchRequestDTO")
public class HephaestusDeviceTaskSearchRequestDTO extends BasePageDTO {

    /**
     *设备类型
     * */
    private String deviceType;
    /**
     * 设备KEY
     */
    private String deviceKey;

}
