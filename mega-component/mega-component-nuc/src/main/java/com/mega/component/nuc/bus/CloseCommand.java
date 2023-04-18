package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class CloseCommand extends AbstractCommand {

    public CloseCommand() {
        this.key = BusCommand.Close;
        this.name = "关闭";
        this.groupName = "BUS";
    }
}
