package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class CreateWarningCommand extends AbstractCommand {

    public CreateWarningCommand() {
        this.key = BusCommand.CreateWarning;
        this.name = "创建警告";
        this.groupName = "BUS";
    }
}
