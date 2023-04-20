package com.mega.hephaestus.pms.nuc.config;

import com.mega.component.nuc.device.DeviceHeartbeatPool;
import com.mega.hephaestus.pms.nuc.manager.DeviceManagerFactory;
import com.mega.hephaestus.pms.nuc.workflow.task.StageRedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

@Import(value = {
        DeviceProperties.class,
        WorkProperties.class,
        DeviceManagerFactory.class,
        StageRedisService.class,
})
//@AutoConfigureOrder(0)
@Configuration(proxyBeanMethods = true)
public class NucAutoConfiguration {

    @Bean
    public DeviceHeartbeatPool deviceHeartbeatPool() {
        return new DeviceHeartbeatPool();
    }

    @Bean
    public ConversionService conversionService() {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new DeviceTypeConverter());
        return conversionService;
    }

}
