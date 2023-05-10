export type PlateDynamic = {
  standard: Array<PlateItem>;
  pipette1: Array<PlateItem>;
  sample: Array<PlateItem>;
  pipette2: Array<PlateItem>;
  empty: Array<PlateItem>;
}


export type PlateItem = {
  deviceType: string;
  consumeTime: string;
  experimentPoolId: number;
  finishTime: string;
  experimentGroupId: number;
  experimentPoolType: string;
  plateNo: number;
  transientTime: string;
  deviceKey: string;
  transientExperimentPoolTypeName: string;
  isFinished: string;
  barCode: any;
  transientStatus: number;
  isConsumed: string;
  instanceId: number;
  createTime: string;
  experimentGroupHistoryId: number;
  transientSort: any;
  experimentId: number;
  id: number;
  experimentName: string;
  experimentPlateStorageId: number;
}

