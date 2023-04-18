package com.mega.component.mybatis.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 胡贤明
 */
@Data
//@ApiModel("BaseDTO")
public class BaseDTO implements Serializable {
    // DTO父类，统一进行和Entity、VO之间相互转换
}