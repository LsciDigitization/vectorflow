package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetStateCommand extends AbstractCommand {

    public GetStateCommand() {
        this.key = BusCommand.GetState;
        this.name = "获取状态";
        this.groupName = "BUS";
    }
}
