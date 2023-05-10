
//移液数据 对象
export interface TransferData  {
  id: string;
  planId: string;

  sourcePlate: string;
  sourcePlateKey: string;
  sourceWell: string;
  sourceWellKey: string;

  destinationPlate: string;
  destinationPlateKey: string;
  destinationWell: string;
  destinationWellKey: string;

  liquidId: string;
  sampleId: string;
  transferType: string;
  pipetteId: string;


  volume: string;
  transferTime: string;
  transferDescription: string;
}


/**
 * 移液记录 组件所修参数
 */
export type TransferListProps = {
  onCancel: (flag?: boolean) => void;
  onSubmit: (flag?: boolean) => void;
  visible: boolean;
  // well id 孔的id
  wellId: string,
  // type: "sample" | "liquid",
  title: string
};

export type TransferTableProps = {
  onCancel: (flag?: boolean) => void;
  onSubmit: (flag?: boolean) => void;
  visible: boolean;
  // 列表数据
  dataSource: TransferData[],
  // 表格title
  title: string
};

