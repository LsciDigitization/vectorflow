package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class CreateErrorCommand extends AbstractCommand {

    public CreateErrorCommand() {
        this.key = BusCommand.CreateError;
        this.name = "创建错误";
        this.groupName = "BUS";
    }
}
