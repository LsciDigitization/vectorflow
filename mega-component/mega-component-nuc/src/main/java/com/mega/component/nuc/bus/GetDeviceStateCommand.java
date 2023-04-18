package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetDeviceStateCommand extends AbstractCommand {

    public GetDeviceStateCommand() {
        this.key = BusCommand.GetDeviceState;
        this.name = "获取设备状态";
        this.groupName = "BUS";
    }
}
