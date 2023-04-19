package com.mega.hephaestus.pms.data.model.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTag;

import java.util.Optional;


/**
 * 标签表
 *
 * @author xianming.hu
 */
public interface IHephaestusPmsTagService extends IBaseService<HephaestusPmsTag> {

    /**
     * 根据标签名称查询标签
     * @param tagName  标签名称
     * @return HephaestusPmsTag 标签
     */
    Optional<HephaestusPmsTag> getByTagName(String tagName);
}

