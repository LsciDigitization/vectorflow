declare namespace API {
  // 任务动态
  // api/instanceTask/instanceTaskDynamic
  type InstanceTaskDynamic = {
    willExpireDurationSecond: any;
    deviceId: any;
    updatePriorityTime: string;
    stepKey: string;
    instanceId: number;
    transientSort: number;
    experimentGroupHistoryId: number;
    taskNo: number;
    startTime: string;
    id: number;
    taskStatus: string;
    deviceType: string;
    taskErrorMessage: any;
    experimentPoolType: string;
    plateNo: number;
    stepValue: number;
    taskCommand: string;
    nextTaskExpireDurationSecond: number;
    deviceKey: string;
    taskParameter: string;
    timeoutSecond: number;
    priority: number;
    taskRequestId: string;
    createTime: string;
    deviceKeyRange: string;
    taskName: string;
    endTime: any;
    taskId: number;
  }

}
