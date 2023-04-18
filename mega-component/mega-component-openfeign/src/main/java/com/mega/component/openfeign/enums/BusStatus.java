package com.mega.component.openfeign.enums;

public enum BusStatus {
    Success("10000000", "失败"),
    Fail("10000001", "失败"),
    Error("10000002", "错误"),
    Invalid("10000003", "无效"),
    Exist("10000004", "已存在"),
    NotExist("10000005", "不存在"),
    Timeout("10000006", "超时"),
    Refuse("10000007", "拒绝"),
    Repeat("10000008", "重复"),
    NoRight("10000009", "无权"),
    NotFound("1000000A", "没找到"),
    NotSupport("1000000B", "不支持"),
    LostConnect("1000000C", "失去连接"),
    Other("1000000D", "Other"),

    DeviceStateFail("10000110", "设备状态切换失败"),
    DeviceTaskStateFail("10000111", "任务状态切换失败"),
    InitializeFail("10000112", "初始化失败"),
    GateInitializeFail("10000113", "门初始化失败"),
    TrayInitializeFail("10000114", "托架初始化失败"),

    DeviceError("10000120", "设备故障"),
    GateError("10000121", "门故障"),
    TrayError("10000122", "托架故障"),

    DeviceIdInvalid("10000130", "设备ID无效"),
    CommandInvalid("10000131", "命令无效"),
    RequestIdInvalid("10000132", "请求ID无效"),
    ParametersInvalid("10000133", "参数无效"),
    EventIdInvalid("10000134", "事件ID无效"),
    TargetRequestIdInvalid("10000135", "目标请求ID无效"),
    PMSAddressInvalid("10000136", "PMS地址无效"),
    LanguageInvalid("10000137", "语言无效"),
    PlateIdInvalid("10000138", "板ID无效"),
    RotateAngleInvalid("10000139", "旋转角度无效"),

    RequestIdExist("10000140", "请求ID已存在"),

    DeviceIdNotExist("10000150", "设备ID不存在"),
    TargetRequestIdNotExist("10000151", "目标请求ID不存在"),
    EventIdNotExist("10000152", "事件ID不存在"),
    ScriptNotExist("10000153", "脚本不存在"),

    PLCTimeout("10000160", "PLC超时"),
    OpenGateTimeout("10000161", "开门超时"),
    CloseGateTimeout("10000162", "关门超时"),
    OpenTrayTimeout("10000163", "出托架超时"),
    CloseTrayTimeout("10000164", "入托架超时"),

    PLCLostConnect("100001C0", "PLC失去连接"),
    DriverLostConnect("100001C1", "驱动器失去连接"),
    E100001C2("100001C2", "门失去连接"),
    E100001C3("100001C3", "托架失去连接"),

    Aborted("100001D0", "已中止"),
    Busy("100001D1", "忙"),
    HandleBusy("100001D2", "容错忙"),
    SlaveDeviceNotIdle("100001D3", "子设备不闲"),
    NotGoBreak("100001D4", "没有完成善前"),
    NotBackBreak("100001D5", "没有完成善后"),
    EmergencyStop("100001D6", "紧急停止"),
    LightCurtainStop("100001D7", "光幕停止"),
    GateEmergencyStop("100001D8", "门紧急停止"),
    TrayEmergencyStop("100001D9", "托架紧急停止"),

    Exception("100001FF", "异常"),

    E10400064("10400064", "无效的NUC配置"),
    E10400065("10400065", "初始化错误"),
    E10400066("10400066", "将方法解析为Xml错误"),
    E10400067("10400067", "读取未正确完成"),
    E10400068("10400068", "未找到结果文件"),
    E10400069("10400069", "未连接设备"),
    E1040006A("1040006A", "出板失败"),
    E1040006B("1040006B", "进板失败"),
    E1040006F("1040006F", "开门超时错误"),
    E10400070("10400070", "关门超时错误"),
    E10400071("10400071", "导出数据超时错误"),
    E10400072("10400072", "运行超时错误"),
    E10400073("10400073", "获取脚本超时错误"),

    E104003E8("104003E8", "未知错误，请检查NUC和Cyto的日志"),


    ;


    private final String code;
    private final String label;

    BusStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return code;
    }
}
