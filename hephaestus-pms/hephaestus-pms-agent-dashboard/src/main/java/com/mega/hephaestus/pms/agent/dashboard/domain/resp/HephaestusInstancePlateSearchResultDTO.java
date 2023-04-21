package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.component.mybatis.common.model.BaseFullDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author yinyse
 * @description TODO 实例板结果返回
 * @date 2022/11/25 3:01 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("instancePlateSearchResultDTO")
public class HephaestusInstancePlateSearchResultDTO   extends BaseFullDTO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 实验ID
    private Long experimentId;

    // 实验名称
    private String experimentName;


    // 实验组ID
    private Long experimentGroupId;

    // 实验资源池ID
    private Long experimentPoolId;

    // 实验资源池类型
    private String experimentPoolType;


    // 实验板池ID
    private Long experimentPlateStorageId;

    // 实验组历史id
    private Long experimentGroupHistoryId;


    // 设备type
    private String deviceType;

    // 设备key
    private String deviceKey;

    private Long instanceId;

    // 是否消费 0 否 1 是
    private BooleanEnum isConsumed;

    // 消息ID
    private Date consumeTime;
}
