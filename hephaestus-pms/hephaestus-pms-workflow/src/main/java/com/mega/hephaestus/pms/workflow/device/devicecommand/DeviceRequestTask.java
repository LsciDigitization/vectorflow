package com.mega.hephaestus.pms.workflow.device.devicecommand;

import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface DeviceRequestTask {
//    @Deprecated(since = "20230417")
//    void run(final AbstractDevice device, DeviceClient deviceClient, Command command, final String requestId, final Map<String, Object> taskParameterMap);

//    @Deprecated(since = "20230417")
//    void run(final AbstractDevice device, DeviceClient deviceClient, Command command, ResourceTaskEntity deviceTask);

    void run(DeviceRequestTaskParameter deviceRequestTaskParameter);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceRequestTaskParameter {
        public StepType stepType;
        public AbstractDevice device;
        public DeviceClient deviceClient;
        public Command command;
        public ResourceTaskEntity deviceTask;
    }

}
