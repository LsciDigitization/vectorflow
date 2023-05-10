/**
 * @ts-ignore
 */
import request from '@/utils/request';



// 通量动态
export async function getInstanceIterationData() {
  return request(`/instanceIteration/items`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}


