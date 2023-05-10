/**
 * @ts-ignore
 */
import request from '@/utils/request';


// 样品数据
export async function getSampleData() {

  // @ts-ignore
   return request(`/sample/items`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
