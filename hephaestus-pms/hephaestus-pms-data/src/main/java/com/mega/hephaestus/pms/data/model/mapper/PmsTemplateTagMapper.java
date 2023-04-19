package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTemplateTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模板标签关系表
 *
 * @author xianming.hu
 */
@Mapper
public interface PmsTemplateTagMapper extends SuperMapper<HephaestusPmsTemplateTag> {

}
