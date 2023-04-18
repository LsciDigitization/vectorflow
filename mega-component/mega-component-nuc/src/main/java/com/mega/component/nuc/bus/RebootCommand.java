package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class RebootCommand extends AbstractCommand {

    public RebootCommand() {
        this.key = BusCommand.Reboot;
        this.name = "重启";
        this.groupName = "BUS";
    }
}
