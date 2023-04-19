package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 实例板池
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_instance_plate")
@Deprecated(since = "已修改为Instance_labware")
public class HephaestusInstancePlate {

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

    // 消费时间
    private Date consumeTime;

    // 是否完成 0 否 1 是
    private BooleanEnum isFinished;

    /**
     * 板序号
     */
    private Integer plateNo;
    // 完成时间
    private Date finishTime;

    /**
     * 条形码
     */
    private String barCode;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 状态
     *    3、已完成--->isFinished ==1 && isConsumed ==1
     *    2、已消费--->(isFinished ==null || isFinished ==0) && isConsumed ==1
     *    1、未消费--->(isFinished ==null || isFinished ==0) && isConsumed ==0
     */
    @TableField(exist = false)
    private Integer transientStatus;

    @TableField(exist = false)
    private Date transientTime;

    // 排序
    @TableField(exist = false)
    private Integer transientSort;

    @TableField(exist = false)
    private String transientExperimentPoolTypeName;
}
