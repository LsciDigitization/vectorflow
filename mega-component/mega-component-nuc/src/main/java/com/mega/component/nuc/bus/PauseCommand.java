package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class PauseCommand extends AbstractCommand {

    public PauseCommand() {
        this.key = BusCommand.Pause;
        this.name = "暂停";
        this.groupName = "BUS";
    }
}
