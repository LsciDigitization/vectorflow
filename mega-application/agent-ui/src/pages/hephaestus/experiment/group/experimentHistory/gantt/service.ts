/**
 * @ts-ignore
 */
import request from '@/utils/request';





export  function getPlateTypeColor(historyId: string) {

  return request('/resourcePlateType/plateTypeColor/'+historyId,{
    method: 'get'
  })
}


// 获取正在运行 板类型颜色
export  function getPlateTypeColorByRunning() {

  return request('/resourcePlateType/plateTypeByRunning',{
    method: 'get'
  })
}

// 获取正在运行中的甘特图数据
export function getRunningGantt() {

  return request('/instanceTask/getRunningInstanceGantt',{
    method: 'get'
  })
}

/**
 * 获取资源颜色
 * @param historyId
 */
export  function getResourceColor(historyId: string){
  return request('/resource/resourceColor/'+historyId,{
    method: 'get'
  })
}

/**
 * 获取正在运行的实验组 资源颜色
 */
export  function getResourceColorByRunning(){
  return request('/resource/resourceByRunning/',{
    method: 'get'
  })
}


/**
 * 获取每个通量完成率
 */
export  function getCountGroupByPlateNo(){
  return request('/instancePlate/iterationMetrics',{
    method: 'get'
  })
}
