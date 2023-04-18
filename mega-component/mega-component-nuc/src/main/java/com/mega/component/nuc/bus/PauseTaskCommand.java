package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class PauseTaskCommand extends AbstractCommand {

    public PauseTaskCommand() {
        this.key = BusCommand.PauseTask;
        this.name = "暂停任务";
        this.groupName = "BUS";
    }
}
