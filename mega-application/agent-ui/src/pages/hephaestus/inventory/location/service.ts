/**
 * @ts-ignore
 */
import request from '@/utils/request';
import {LabwarePlateParams} from "@/pages/hephaestus/inventory/location/data";



// 获取存储类设备
export async function getStorageDeviceData() {
  return request(`/resource/listStorageDevice`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

export async function getDeviceNestData(deviceKey:string) {
  return request(`/nest/getNest?deviceKey=`+deviceKey, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
export async function getProcessLabwareData(params?: LabwarePlateParams) {
  const queryString = new URLSearchParams(params).toString();
  return request(`/labware/items?${queryString}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
