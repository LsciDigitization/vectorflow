package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetCommandsCommand extends AbstractCommand {

    public GetCommandsCommand() {
        this.key = BusCommand.GetCommands;
        this.name = "获取所有命令";
        this.groupName = "BUS";
    }
}
