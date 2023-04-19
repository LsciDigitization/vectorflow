package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTemplateTag;
import com.mega.hephaestus.pms.data.model.mapper.PmsTemplateTagMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusPmsTemplateTagService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * 模板标签关系表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class HephaestusPmsTemplateTagServiceImpl extends
        ServiceImpl<PmsTemplateTagMapper, HephaestusPmsTemplateTag> implements IHephaestusPmsTemplateTagService {

}
