package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签表
 *
 * @author xianming.hu
 */
@Mapper
public interface PmsTagMapper extends SuperMapper<HephaestusPmsTag> {

}
