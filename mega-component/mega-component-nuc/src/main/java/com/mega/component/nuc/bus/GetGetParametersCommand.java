package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetGetParametersCommand extends AbstractCommand {

    public GetGetParametersCommand() {
        this.key = BusCommand.GetGetParameters;
        this.name = "获取所有参数";
        this.groupName = "BUS";
    }
}
