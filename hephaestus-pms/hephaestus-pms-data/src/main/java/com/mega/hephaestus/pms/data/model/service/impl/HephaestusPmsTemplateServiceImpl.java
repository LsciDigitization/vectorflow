package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTemplate;
import com.mega.hephaestus.pms.data.model.mapper.PmsTemplateMapper;
import com.mega.hephaestus.pms.data.model.service.IHephaestusPmsTemplateService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * pms模板表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class HephaestusPmsTemplateServiceImpl extends
        ServiceImpl<PmsTemplateMapper, HephaestusPmsTemplate> implements IHephaestusPmsTemplateService {

    /**
     * 根据模板名称+设备id+设备命令查询
     *
     * @param templateName  模板名称
     * @param deviceId      设备id
     * @param deviceCommand 设备命令
     * @return HephaestusPmsTemplate 模板对象
     */
    @Override
    public Optional<HephaestusPmsTemplate> getByTemplateNameAndDeviceIdAndDeviceCommand(String templateName, long deviceId, String deviceCommand) {
        HephaestusPmsTemplate one = lambdaQuery().eq(HephaestusPmsTemplate::getTemplateName, templateName)
                .eq(HephaestusPmsTemplate::getDeviceId, deviceId)
                .eq(HephaestusPmsTemplate::getDeviceCommand, deviceCommand)
                .one();
        return Optional.ofNullable(one);
    }
}
