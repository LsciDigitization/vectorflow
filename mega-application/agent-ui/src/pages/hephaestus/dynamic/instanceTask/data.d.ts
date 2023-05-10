export type InstanceTaskType = {
  id: number;
  deviceKey: string;
  deviceKeyRange: number;
  priority: string;
  deviceId: string;
  deviceType: string;
  taskStatus: string;
  taskCommand: string;
  taskParameter: string;
  instanceId: number;
  taskId: number;
  taskName: string;
  taskRequestId: string;
  taskErrorMessage: string;
  createTime: string;
  startTime: string;

  endTime: string;
  updatePriorityTime: string;

  timeoutSecond: number;
  nextTaskExpireDurationSecond: number;
  willExpireDurationSecond: number;
  stepKey: string;
  stepValue: number;
  experimentPoolType: string;
  taskNo: number;
  transientSort: number;
  plateNo: number;
  experimentGroupHistoryId: number
};

export type InstanceTaskListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type InstanceTaskListData = {
  list: InstanceTaskType[];
  pagination: Partial<InstanceTaskListPagination>;
};

