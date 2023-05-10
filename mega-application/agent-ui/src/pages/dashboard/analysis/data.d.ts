import {DataItem} from '@antv/g2plot/esm/interface/config';

export {DataItem};

export interface VisitDataType {
  x: string;
  y: number;
}

export type SearchDataType = {
  index: number;
  keyword: string;
  count: number;
  range: number;
  status: number;
};

export type OfflineDataType = {
  name: string;
  cvr: number;
};

export interface OfflineChartData {
  date: number;
  type: number;
  value: number;
}

export type RadarData = {
  name: string;
  label: string;
  value: number;
};

export interface AnalysisData {
  visitData: DataItem[];
  visitData2: DataItem[];
  salesData: DataItem[];
  searchData: DataItem[];
  offlineData: OfflineDataType[];
  offlineChartData: DataItem[];
  salesTypeData: DataItem[];
  salesTypeDataOnline: DataItem[];
  salesTypeDataOffline: DataItem[];
  radarData: RadarData[];
}

// 通量详情
export interface InstancePlateNoCountData {
  plateNo: string;
  total: string;
  finishedTotal: string;
  completionRate: number;
}

// 仪表盘数据
export interface DashboardData {
  projectName: string;
  projectStatus: string;
  projectDescription: string;
  links: LinkData[]


}

// 相关链接
export interface LinkData {
  url: string;
  linkName: string;

}

// 板池汇总
export interface InstancePlateNoSummaryData {
  total: number;
  finishedTotal: number;
  consumedTotal: number;
  startTime: Date;
  endTime: Date;
  startTimeFormat: string;
  endTimeFormat: string;
  durationTimeFormat: string;
}


// 任务总数
export interface InstanceTaskCountData {
  total: number;
  finishedTotal: number;
  runningTotal: number;
}
