/**
 * @ts-ignore
 */
import request from '@/utils/request';



export async function getUnFinishedInstanceTask(){
  return request('/instanceTask/instanceTaskDynamicByRunning',{
    method: 'get'
  })
}


export function ganttHistoryId(historyId: string) {

  return request('/instanceTask/gantt/'+historyId,{
    method: 'get'
  })
}

