package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class ContinueTaskCommand extends AbstractCommand {

    public ContinueTaskCommand() {
        this.key = BusCommand.ContinueTask;
        this.name = "继续任务";
        this.groupName = "BUS";
    }
}
