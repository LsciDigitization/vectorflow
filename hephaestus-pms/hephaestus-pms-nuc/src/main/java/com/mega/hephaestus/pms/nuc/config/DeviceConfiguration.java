package com.mega.hephaestus.pms.nuc.config;

import com.mega.component.nuc.device.DeviceType;
import lombok.Data;

@Data
public class DeviceConfiguration {
    // 设备类型
    private DeviceType deviceType;

    private String deviceKey;

    // 设备别名
    private String deviceAlias;

    // 设备ID
    private String deviceId;

    // 设备机器URL
    private String url;

    // 设备回调地址
    private String callbackUrl;
}
