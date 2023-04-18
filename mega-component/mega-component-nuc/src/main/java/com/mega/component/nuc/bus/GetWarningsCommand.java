package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetWarningsCommand extends AbstractCommand {

    public GetWarningsCommand() {
        this.key = BusCommand.GetWarnings;
        this.name = "获取警告";
        this.groupName = "BUS";
    }
}
