package com.mega.component.openfeign.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableFeignClients(basePackages = {"com.mega.component.openfeign"})
@Import({
        ClientConfiguration.class,
})
@ComponentScan(basePackages = {"com.mega.component.openfeign"})
@AutoConfigureOrder(0)
public class OpenFeignAutoConfiguration {
}
