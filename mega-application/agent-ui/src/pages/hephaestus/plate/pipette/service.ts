/**
 * @ts-ignore
 */
import request from '@/utils/request';


// 移液记录数据
export async function getPipetteData() {

  // @ts-ignore
   return request(`/pipette/items`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
