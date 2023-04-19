package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;


@Data
@TableName("hephaestus_instance_task_result")
public class InstanceTaskResultEntity {

  /**
   * 主键
   */
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 实例id
   */
  private Long instanceId;


  /**
   * 任务id
   */
  private Long taskId;

  /**
   * 记录Id
   */
  private Long processRecordId;

  /**
   * 步骤key
   */
  private String stepKey;

  /**
   * 执行状态
   */
  private String executionStatus;

  /**
   * 执行结果
   */
  private String executionResult;

  /**
   * 版本号
   */
  private Integer version;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;}

