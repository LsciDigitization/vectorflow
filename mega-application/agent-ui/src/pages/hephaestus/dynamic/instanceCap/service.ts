/**
 * @ts-ignore
 */
import request from '@/utils/request';



// 开盖机动态
export async function capDynamic() {
  return request(`//instanceCap/capDynamic`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

