package com.mega.hephaestus.pms.nuc.client;

import com.mega.component.nuc.command.RequestCommandInterface;
import com.mega.component.nuc.device.DeviceClient;
import com.mega.component.nuc.message.Response;
import org.springframework.web.bind.annotation.PostMapping;

public interface GenericDeviceClient extends DeviceClient {
    /**
     * GenericDeviceClient 发送指令
     * @param requestCommand 参数
     * @return 返回
     */
    @PostMapping("/api/BusFrame/System/Post")
    Response executeCommand(RequestCommandInterface requestCommand);
}
