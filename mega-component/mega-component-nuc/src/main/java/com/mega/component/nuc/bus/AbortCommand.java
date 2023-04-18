package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class AbortCommand extends AbstractCommand {

    public AbortCommand() {
        this.key = BusCommand.Abort;
        this.name = "终止";
        this.groupName = "BUS";
    }
}
