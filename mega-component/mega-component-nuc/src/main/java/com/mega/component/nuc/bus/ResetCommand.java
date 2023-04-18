package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class ResetCommand extends AbstractCommand {

    public ResetCommand() {
        this.key = BusCommand.Reset;
        this.name = "重置";
        this.groupName = "BUS";
    }

}
