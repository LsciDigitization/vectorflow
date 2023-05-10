declare namespace API {
  // 板池动态
  // api/instancePlate/plateDynamic
  type PlateDynamic = {
    standard: PlateItem[];
    pipette1: PlateItem[];
    sample: PlateItem[];
    pipette2: PlateItem[];
    empty: PlateItem[];
  }


  type PlateItem = {
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

}
