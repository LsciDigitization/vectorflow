/**
 * @ts-ignore
 */
import request from '@/utils/request';
import { TransferPlanParams} from "@/pages/hephaestus/plate/transferPlan/data";


// 移液记录数据
export async function getTransferPlanData(params?: TransferPlanParams) {
  const queryString = new URLSearchParams(params).toString();

  // @ts-ignore
   return request(`/transferPlan/items?${queryString}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
export async function getTransferPlanDataById(id: string) {

  // @ts-ignore
  return request(`/transferPlan/`+id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
