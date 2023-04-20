package com.mega.hephaestus.pms.workflow.device.devicecommand;

import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceClient;
import com.mega.hephaestus.pms.workflow.task.taskmodel.BuiltinGatewayModel;
import com.mega.hephaestus.pms.workflow.task.stagetask.TaskParameterSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceNoneRunScriptHandler {

    private final TaskParameterSerializer taskParameterSerializer;

    public void run(AbstractDevice device, DeviceClient deviceClient, Command command, ResourceTaskEntity deviceTask) {
        // TODO 未实现
        // Gateway 判断任务
        BuiltinGatewayModel.GatewayTaskParameter gatewayTaskParameter = taskParameterSerializer.deserialize(deviceTask.getTaskParameter(), BuiltinGatewayModel.GatewayTaskParameter.class);
        System.out.println(gatewayTaskParameter);
    }
}
