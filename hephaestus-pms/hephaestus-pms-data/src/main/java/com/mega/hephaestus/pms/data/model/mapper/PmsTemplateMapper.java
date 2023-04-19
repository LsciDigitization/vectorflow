package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
 * pms模板表
 *
 * @author xianming.hu
 */
@Mapper
public interface PmsTemplateMapper extends SuperMapper<HephaestusPmsTemplate> {

}
