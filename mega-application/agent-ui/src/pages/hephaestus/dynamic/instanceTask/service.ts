/**
 * @ts-ignore
 */
import request from '@/utils/request';



// 任务动态
export async function instanceTaskList() {
  return request(`/instanceTask/instanceTaskDynamic`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

// 任务统计
export async function instanceTaskCount() {
  return request(`/instanceTask/countInstanceTasks`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}
