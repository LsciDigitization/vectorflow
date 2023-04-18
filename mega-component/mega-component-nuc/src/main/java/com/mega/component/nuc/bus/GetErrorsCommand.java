package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetErrorsCommand extends AbstractCommand {

    public GetErrorsCommand() {
        this.key = BusCommand.GetErrors;
        this.name = "获取所有错误";
        this.groupName = "BUS";
    }
}
