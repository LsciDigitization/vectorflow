/**
 * @ts-ignore
 */
import request from '@/utils/request';
import {TransferParams} from "@/pages/hephaestus/plate/transfer/data";


// 移液记录数据
export async function getTransferData(params?: TransferParams) {
  const queryString = new URLSearchParams(params).toString();

  // @ts-ignore
   return request(`/transfer/items?${queryString}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
