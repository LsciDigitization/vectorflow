
// 甘特图行数据
export interface GanttRow {
  // 行索引和id一样即可
  rawIndex: number;
  // 左侧颜色
  colorPair: string;
  speed: number;
  // 右侧任务数组
  gtArray: TaskItem[];
  id: number;
  // 左侧名称
  leftTitle: string;
}

export interface TaskItem {
  // 任务id
  id: string;
  // 任务开始时间
  start: string;
  // 任务结束时间
  end: string;
  // 任务名称
  name: string;
  // 父id 即行的id
  parentId: number;
  // 设备类型
  deviceType: string;
  // 设备key
  deviceKey: string;
  color: string,
  // 状态
  status: number;
}

export interface ColorPair {
  light: string;
  dark: string
}

