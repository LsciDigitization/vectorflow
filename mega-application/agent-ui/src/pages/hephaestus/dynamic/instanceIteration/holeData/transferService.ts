/**
 * @ts-ignore
 */
import request from '@/utils/request';



//  移液数据 包含 样本和 液体
export async function getTransferData(wellId: string) {
  // @ts-ignore
   return request(`/transfer/getByWellId?wellId=`+wellId, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}


