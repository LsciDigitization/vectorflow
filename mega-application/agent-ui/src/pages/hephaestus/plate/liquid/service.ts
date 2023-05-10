/**
 * @ts-ignore
 */
import request from '@/utils/request';
import {LiquidData} from "@/pages/hephaestus/plate/liquid/data";


// 移液记录数据
export async function getLiquidData() {

  // @ts-ignore
   return request(`/liquid/items`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
