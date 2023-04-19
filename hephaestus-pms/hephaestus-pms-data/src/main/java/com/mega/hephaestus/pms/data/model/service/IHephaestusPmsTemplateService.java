package com.mega.hephaestus.pms.data.model.service;

import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.model.entity.HephaestusPmsTemplate;

import java.util.Optional;


/**
 * pms模板表
 *
 * @author xianming.hu
 */
public interface IHephaestusPmsTemplateService extends IBaseService<HephaestusPmsTemplate> {


    /**
     *  根据模板名称+设备id+设备命令查询
     * @param templateName 模板名称
     * @param deviceId 设备id
     * @param deviceCommand 设备命令
     * @return HephaestusPmsTemplate 模板对象
     */
    Optional<HephaestusPmsTemplate> getByTemplateNameAndDeviceIdAndDeviceCommand(String templateName, long deviceId, String deviceCommand);
}

