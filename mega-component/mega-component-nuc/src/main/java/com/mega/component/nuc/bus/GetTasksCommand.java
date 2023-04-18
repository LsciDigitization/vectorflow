package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

public class GetTasksCommand extends AbstractCommand {

    public GetTasksCommand() {
        this.key = BusCommand.GetTasks;
        this.name = "获取任务";
        this.groupName = "BUS";
    }
}
