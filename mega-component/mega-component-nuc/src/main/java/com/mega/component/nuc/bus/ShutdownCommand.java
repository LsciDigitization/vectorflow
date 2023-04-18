package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class ShutdownCommand extends AbstractCommand {

    public ShutdownCommand() {
        this.key = BusCommand.Shutdown;
        this.name = "关机";
        this.groupName = "BUS";
    }
}
