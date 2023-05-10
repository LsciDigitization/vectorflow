
declare namespace API {
  // 设备管理列表
  // api/device/list
  type DeviceList = {
    deviceType: string;
    serverAddress: string;
    deviceKey: string;
    description: any;
    updateTime: string;
    updateName: any;
    deviceId: string;
    deviceName: string;
    deviceTypeFamily: string;
    serverProxyAddress: any;
    callbackAddress: string;
    deviceStatus: string;
    createBy: number;
    isDeleted: string;
    lockStatus: string;
    createTime: string;
    updateBy: number;
    deviceModel: string;
    id: number;
    createName: any;
    remarks: any;
    status: string;
  }

  // 设备存储池列表接口
  // api/storage/list
  type StorageList = {
    storageDeviceType: string;
    deviceKey: string;
    updateTime: string;
    storageStatus: string;
    updateName: any;
    storageKey: string;
    storageName: string;
    createBy: any;
    instanceId: any;
    isDeleted: string;
    createTime: any;
    updateBy: number;
    poolId: any;
    id: number;
    createName: any;
    remarks: any;
  }

}
