package com.mega.hephaestus.pms.workflow.device.devicebus;

import com.mega.component.nuc.device.DeviceType;
import lombok.Data;

import java.util.List;

@Data
public class DeviceResourceModel {

    private Long id;

    private DeviceType deviceType;

    private String deviceAlias;

    private String deviceKey;

    private String deviceName;

    private String deviceId;

    private String callbackUrl;

    private String url;

    // 资源状态
    private DeviceResourceStatus resourceStatus;
    // 是否资源瓶颈，默认都是false
    private Boolean resourceBottleneck;
    // 资源带板数
    private List<Integer> resourcePlateNumber;

}
