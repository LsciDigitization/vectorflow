package com.mega.component.nuc.device;


import com.mega.component.nuc.command.RequestCommandInterface;
import com.mega.component.nuc.message.Response;

public interface DeviceClient {

    Response executeCommand(RequestCommandInterface requestCommand);

}
