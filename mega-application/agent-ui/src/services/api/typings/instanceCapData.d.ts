
declare namespace API {
  // 开关机动态
  // api/instanceCap/capDynamic
  type InstanceCapDynamic = {
    id: number;

    instanceId: number;

    experimentGroupHistoryId: number;

    deviceKey: string;

    deviceType: string;

    openCapTime: string;

    closeCapTime: string;

    deviceStatus: number;
  }

}
