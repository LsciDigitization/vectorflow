/**
 * @ts-ignore
 */
import request from '@/utils/request';



// 板池动态
export async function instancePlateDynamic() {
  return request(`/instancePlate/plateDynamic`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

// 板池数据统计
export async function plateCount() {
  return request(`/instancePlate/countRunningConsumables`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
