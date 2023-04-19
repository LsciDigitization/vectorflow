package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实验组
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface ProcessRecordMapper extends SuperMapper<ProcessRecordEntity> {

}
