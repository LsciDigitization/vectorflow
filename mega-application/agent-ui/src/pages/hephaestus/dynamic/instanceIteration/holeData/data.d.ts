
export interface InstancePlateData  {
  id: string;
  projectId: string;

  devicePoolTypeId: string;
  deviceKey: string;
  experimentId: string;
  experimentName: string;

  experimentGroupId: string;
  experimentPoolId: string;
  experimentPoolType: string;
  experimentPlateStorageId: string;


  experimentGroupHistoryId: string;
  deviceType: string;
  instanceId: string;
  isConsumed: string;
  plateNo: string;
}

export type InstancePlateParams = {
  experimentPoolType?: string;

}

/**
 * 孔位数据历史
 */
export type HoleDataHistoryTableProps = {
  onCancel: (flag?: boolean) => void;
  onSubmit: (flag?: boolean) => void;
  visible: boolean;
  holeDataId: string;
  dataSource: any[],
  title: string
};
// 孔位历史数据
export interface HoleHistoryData  {
  id: string;
  projectId: string;
  holeDataId: string;
  plateType: string;
  instancePlateId: string;
  capacity: string;
  concentration: string;

  data: string;
  dataType: string;
  version: string;
  holeKey: string;


  createTime: string;
}

