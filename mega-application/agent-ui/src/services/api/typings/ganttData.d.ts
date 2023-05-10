declare namespace API {
  // 甘特图
  // api/instanceTask/gantt/{historyId}
  type GanttList = {
    instances: InstanceItem[];
    startTime: string;
    endTime: string;
  }

  type InstanceItem = {
    batchNo: any;
    instanceId: string;
    instanceName: string;
    experimentPoolType: string;
    createTime: string;
    plateNo: number;
    experimentPoolTypeName: string;
    tasks: TaskItem[];
  }

  type TaskItem = {
    deviceType: string;
    createTime: string;
    deviceKey: string;
    startTime: string;
    taskName: string;
    endTime: string;
    taskId: string;
    taskStatus: string;
  }


  // 实例动态
  // api/instanceTask/instanceTaskDynamicByHistoryId/{historyId}
  type InstanceTaskDynamic = {
    batchNo: number;
    instanceId: number;
    durationTotal: number;
    plateNo: number;
    waitDurationTotal: number;
    runDurationTotal: number;
    taskList: TaskListItem[];
    instanceTitle: string;
    poolType: string;
    instanceStatus: number;
    durationTotalFormat: any;
  }


  type TaskListItem = {
    virtual: boolean;
    deviceKey: string;
    endTimeFormatted: string;
    startTimeFormatted: string;
    runDuration: number;
    stepKey: string;
    waitDuration: number;
    createTime: string;
    taskNo: number;
    taskName: string;
    startTime: string;
    endTime: string;
    taskStatus: string;
  }

}
