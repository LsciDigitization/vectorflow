package com.mega.component.nuc.command;


import com.mega.component.nuc.message.EnumConst;

public enum BusCommand implements Command {

    Abort,
    AbortTask,
    Close,
    Continue,
    ContinueTask,
    CreateError,
    CreateWarning,
    GetCommands,
    GetDeviceState,
    GetDeviceInfo,
    GetErrors,
    GetGetParameters,
    GetParameters,
    GetSetParameters,
    GetState,
    GetTasks,
    GetTaskState,
    GetWarnings,
    Pause,
    PauseTask,
    Reboot,
    Reset,
    SetParameters,
    Shutdown,
    ;


    public static BusCommand toEnum(final String value) {
        for (BusCommand versionEnum: BusCommand.values()) {
            if (versionEnum.name().equals(value)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, value));
    }

}
