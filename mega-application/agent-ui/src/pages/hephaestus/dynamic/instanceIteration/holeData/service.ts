/**
 * @ts-ignore
 */
import request from '@/utils/request';



// 孔位数据
export async function getHoleDataByPlateId(plateId: string) {
  // @ts-ignore
   return request(`/holeData/getByPlateId?plateId=`+plateId, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

export async function getSampleData(id: string){

  return request(`/sample/`+id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}




// 孔位历史数据
export async function getHoleDataHistoryData(holeDataId: string) {
  // @ts-ignore
  return request(`/holeData/history/items?holeDataId=`+holeDataId, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
