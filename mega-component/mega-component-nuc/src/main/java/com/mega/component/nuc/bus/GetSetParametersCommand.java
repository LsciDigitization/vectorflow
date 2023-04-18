package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetSetParametersCommand extends AbstractCommand {

    public GetSetParametersCommand() {
        this.key = BusCommand.GetSetParameters;
        this.name = "获取设置参数";
        this.groupName = "BUS";
    }
}
