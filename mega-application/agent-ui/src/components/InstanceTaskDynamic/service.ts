/**
 * @ts-ignore
 */
import request from '@/utils/request';


// 实例动态
export async function instanceTaskDynamic(instanceId: string |number) {

  return request('/instanceTask/detail/'+instanceId, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
}

