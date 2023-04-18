package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;

/**
 * @author 胡贤明
 */
public class GetDeviceInfoCommand extends AbstractCommand {
    public GetDeviceInfoCommand() {

        this.key = BusCommand.GetDeviceInfo;
        this.name = "设备资源";
        this.groupName = "BUS";
    }
}
