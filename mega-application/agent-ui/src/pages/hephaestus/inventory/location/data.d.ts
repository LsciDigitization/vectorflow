

export interface StorageLeftMenuData {
  icon: string;
  title: string;
  label: string;
  key: string
}


export interface StorageCommandData {
  key: string;
  name: string;
  disable: boolean;
}

// 板位存储
export interface StorageData {
  // id
  id: string;
  // 板位
  nest: string;
  // 耗材
  plate: string;
  // 条码
  barcode: string;
  // 状态
  status: string;
  // 通量
  flux: number;
  // 过期
  expired: string;
  // 取消
  cancel: string;
}

export interface LabwarePlateData {
  id: string,
  plateType: string,
  labwareNestId: string,
  startLocation: string,
  barCode: string,
  sortOrder: number

}


export type LabwarePlateParams = {
  deviceKey?: string;
  pageSize?: string;
  currentPage?: string;
}

