/**
 * @ts-ignore
 */
import request from '@/utils/request';



// 设备动态
export async function deviceTaskDynamic() {
  return request(`/deviceTask/deviceTaskDynamic`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

