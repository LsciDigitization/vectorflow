package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTag;
import com.mega.hephaestus.pms.data.model.mapper.PmsTagMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusPmsTagService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 标签表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class HephaestusPmsTagServiceImpl extends
        ServiceImpl<PmsTagMapper, HephaestusPmsTag> implements IHephaestusPmsTagService {
    /**
     * 根据标签名字获取标签对象
     * @param tagName 标签名字
     * @return HephaestusPmsTag 标签对象
     */
    @Override
    public Optional<HephaestusPmsTag> getByTagName(String tagName) {
        HephaestusPmsTag one = lambdaQuery().eq(HephaestusPmsTag::getTagName, tagName).one();
        return Optional.ofNullable(one);
    }
}
