export interface IterationData   {
  id: string,

  projectId: string,

  processId: string,

  iterationNo: number,

  consumeId: string,

  instanceId: string,

  processRecordId: string,

  isConsumed: string;

  consumeTime: string,

  isFinished: string,

  finishTime: string,

  // 耗材总数
  labwareTotal: number,

  // 完成总数
  finishedTotal: number,

  labwares : LabwareData[]
};


export interface LabwareData {
  id: string,
  labwareType: string,
  labwareName: string,
  labwareColor: string,
}
