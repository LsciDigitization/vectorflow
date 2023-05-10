
export type Instance = {
  InstanceDynamics: InstanceDynamic[] | [];
  title?: string | ''
}

export type InstanceDynamic = {
  batchNo: number;
  instanceId: number;
  durationTotal: number;
  plateNo: number;
  waitDurationTotal: number;
  runDurationTotal: number;
  taskList: TaskItem[] |[];
  instanceTitle: string;
  poolType: string;
  instanceStatus: number;
  durationTotalFormat: any;
}


export type TaskItem =  {
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
