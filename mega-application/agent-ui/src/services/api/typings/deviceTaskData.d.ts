
declare namespace API {
  // 设备动态
  // api/deviceTask/deviceTaskDynamic
  type DeviceTaskDynamic = {
    deviceType: string;
    taskErrorMessage: any;
    taskDuration: any;
    taskCommand: string;
    deviceKey: string;
    taskParameter: any;
    deviceId: any;
    taskRequestId: string;
    taskResponseMessage: any;
    taskTimeoutSecond: number;
    instanceId: number;
    stageName: any;
    createTime: string;
    experimentGroupHistoryId: any;
    taskName: string;
    startTime: string;
    experimentId: any;
    id: number;
    endTime: any;
    taskId: number;
    taskStatus: string;
    stageId: any;
  }


  // 设备任务
  // api/deviceTask/list
  type DeviceTaskList = {
    deviceId: any;
    taskResponseMessage: any;
    taskTimeoutSecond: number;
    instanceId: number;
    stageName: any;
    isDeleted: any;
    updateBy: any;
    startTime: string;
    experimentId: any;
    id: number;
    taskStatus: string;
    stageId: any;
    deviceType: string;
    taskErrorMessage: any;
    taskDuration: number;
    taskCommand: string;
    deviceKey: string;
    updateTime: any;
    taskParameter: string;
    updateName: any;
    taskRequestId: string;
    createBy: any;
    createTime: string;
    taskName: string;
    endTime: string;
    createName: any;
    remarks: any;
    taskId: number;
  }

  // 任务大盘
  // api/storage/list
  type StorageList = {
    storageDeviceType: string;
    deviceKey: string;
    updateTime: string;
    storageStatus: string;
    updateName: any;
    storageKey: string;
    storageName: string;
    createBy: any;
    instanceId: any;
    isDeleted: string;
    createTime: any;
    updateBy: number;
    poolId: any;
    id: number;
    createName: any;
    remarks: any;
  }
}
