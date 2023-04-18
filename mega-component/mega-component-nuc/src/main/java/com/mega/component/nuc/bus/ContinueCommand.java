package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class ContinueCommand extends AbstractCommand {

    public ContinueCommand() {
        this.key = BusCommand.Continue;
        this.name = "继续";
        this.groupName = "BUS";
    }
}
