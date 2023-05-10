/**
 * @ts-ignore
 */
import request from '@/utils/request';
import {LiquidData} from "@/pages/hephaestus/plate/liquid/data";


// 移液记录数据
export async function getGraphData(batchNo:string) {

  // @ts-ignore
   return request(`/flow/getFlatNode?batchNo=`+batchNo, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}


export async function getEdgesData(batchNo:string) {

  // @ts-ignore
  return request(`/flow/getEdges?batchNo=`+batchNo, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

export async function getIterationGroup() {

  // @ts-ignore
  return request(`/instanceIteration/getIterationGroup`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
