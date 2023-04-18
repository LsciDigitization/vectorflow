package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetTaskStateCommand extends AbstractCommand {

    public GetTaskStateCommand() {
        this.key = BusCommand.GetTaskState;
        this.name = "获取任务状态";
        this.groupName = "BUS";
    }
}
