package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class AbortTaskCommand extends AbstractCommand {

    public AbortTaskCommand() {
        this.key = BusCommand.AbortTask;
        this.name = "终止任务";
        this.groupName = "BUS";
    }
}
