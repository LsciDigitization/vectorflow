package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTraceLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * PMS操作日志
 *
 * @author xianming.hu
 */
@Mapper
public interface PmsTraceLogMapper extends SuperMapper<HephaestusPmsTraceLog> {

}
