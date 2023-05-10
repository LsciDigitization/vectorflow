import type {DataNode} from 'antd/lib/tree';
import {parse} from 'querystring';

export const LoginPageUrl = '/user/login';

const reg =
  /(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+(?::\d+)?|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/;

export const isUrl = (path: string): boolean => reg.test(path);

export const isAntDesignPro = (): boolean => {
  if (ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION === 'site') {
    return true;
  }
  return window.location.hostname === 'preview.pro.ant.design';
};

// 给官方演示站点用，用于关闭真实开发环境不需要使用的特性
export const isAntDesignProOrDev = (): boolean => {
  const { NODE_ENV } = process.env;
  if (NODE_ENV === 'development') {
    return true;
  }
  return isAntDesignPro();
};

export function trim(x: string) {
  return x.replace(/^\s+|\s+$/gm,'');
}

/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param name
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param parentName
 * @param {*} children 孩子节点字段 默认 'children'
 */
export function buildTreeData(
  data: any[],
  id: string,
  name: string,
  parentId: string,
  parentName: string,
  children: string,
) {
  const config = {
    id: id || 'id',
    name: name || 'name',
    parentId: parentId || 'parentId',
    parentName: parentName || 'parentName',
    childrenList: children || 'children',
  };

  const childrenListMap = {};
  const nodeIds = {};
  const tree: any[] = [];
  data.forEach((item: { id: string; name: string; key: string; title: string; value: any }) => {
    const d = item;
    const pId = d[config.parentId];
    if (childrenListMap[pId] == null) {
      childrenListMap[pId] = [];
    }
    d.key = d[config.id];
    d.title = d[config.name];
    d.value = d[config.id];
    nodeIds[d[config.id]] = d;
    childrenListMap[pId].push(d);
  });

  data.forEach((item: any) => {
    const d = item;
    const pId = d[config.parentId];
    if (nodeIds[pId] == null) {
      d[config.parentName] = '';
      tree.push(d);
    }
  });

  tree.forEach((t: any) => {
    adaptToChildrenList(t);
  });

  function adaptToChildrenList(item: any) {
    const o = item;
    if (childrenListMap[o[config.id]] !== null) {
      o[config.childrenList] = childrenListMap[o[config.id]];
    }
    if (o[config.childrenList]) {
      o[config.childrenList].forEach((child: any) => {
        const c = child;
        c[config.parentName] = o[config.name];
        adaptToChildrenList(c);
      });
    }
  }
  return tree;
}

export const getPageQuery = () => parse(window.location.href.split('?')[1]);

export function formatTreeSelectData(arrayList: any): DataNode[] {
  return arrayList.map((item: any) => {
    const node: DataNode = {
      id: item.id,
      title: item.label,
      key: item.id,
      value: item.id
    } as DataNode;
    if (item.children) {
      node.children = formatTreeSelectData(item.children);
    }
    return node;
  });
}

/**
 * 随机生成数字
 * @param min 最小值
 * @param max 最大值
 * @return int 生成后的数字
 */
export function randomNumber (min: number, max: number) {
  return Math.floor(Math.random() * (max - min + 1) + min)
}

/**
 * 随机生成字符串
 * @param length 字符串的长度
 * @param chats 可选字符串区间（只会生成传入的字符串中的字符）
 * @return string 生成的字符串
 */
export function randomString (length: number, chats: string) {
  // eslint-disable-next-line no-param-reassign
  if (!length) length = 1
  // eslint-disable-next-line no-param-reassign
  if (!chats) chats = '0123456789qwertyuioplkjhgfdsazxcvbnm'
  let str = ''
  for (let i = 0; i < length; i++) {
    const num = randomNumber(0, chats.length - 1)
    str += chats[num]
  }
  return str
}

/**
 * 随机生成uuid
 * @return string 生成的uuid
 */
export function randomUUID () {
  const chats = '0123456789abcdef'
  return randomString(32, chats)
}
export function download(fileName: string) {
	window.location.href = `/common/download?fileName=${encodeURI(fileName)}&delete=${  true}`;
}

/**
 *
 * @param {*} year
 * @param {*} month
 * @returns 获取当前月一共多少天
 */
export const getMonthDays = (year: number, month: number) => new Date(year, month, 0).getDate();


/**
 *
 * @param {*} list
 * @returns 对数据排序，startTime从小到大
 */
export const sortData = (list: any) => list.sort((a: any, b: any) => a.startTime - b.startTime);


/**
 *
 * @param {*} list 需要格式化的数据
 * @param {*} day 格式化中需要当月的时间数据
 * @returns 对数据进行同轨道整合-紧凑模式下
 */
export const formatTrack = (list: any, day: any) => {
  const res: any[][] = [];
  list.forEach((item: any) => {
    item.__left = (item.startTime - day.currentMonthFirstDay) / 86400000;
    item.__width = (item.endTime - item.startTime) / 86400000;
    //  开始时间边界处理，加标识 relativeCurrentMonth=prv：开始时间早于当月月初
    if (day.currentMonthFirstDay > item.startTime) {
      item.relativeCurrentMonth = "prv";
      item.__left = 0;
      item.__width = (item.endTime - day.currentMonthFirstDay) / 86400000;
    }
    //  结束时间边界处理，加标识 relativeCurrentMonth=next：结束时间晚于当月月未
    if (day.currentMonthLastDay < item.endTime) {
      item.relativeCurrentMonth = "next";
      item.__width = (day.currentMonthLastDay - item.startTime) / 86400000;
    }
    addTrack(0, item);
  });
  function addTrack(index: any, item: any) {
    const track = res[index];
    const length = track?.length;
    if (length) {
      const _last = track[length - 1];
      if (_last.endTime <= item.startTime) {
        item.__left = (item.startTime - _last.endTime) / 86400000;
        track.push(item);
      } else {
        addTrack(index + 1, item);
      }
    } else {
      res.push([item])
    }
  }
  return res;
};

/**
 *
 * @param {*} h 小时
 * @param {*} m 分钟
 * @returns 小时或分钟未满10，补0
 */
export const formatHMTime = (h: number, m: number) => {
  return `${h > 9 ? h : '0' + h}:${m > 9 ? m : '0' + m}`
};

/**
 * 获取url中指定参数
 * @param url
 * @param key
 */
export function getUrlParamsByKey(url: string,key: string){
  const urlParams = getUrlParams(url)
  if(urlParams){
    return urlParams[key]
  }else {
    return undefined
  }
}
export function getUrlParams(url: string) {
  const urlStr = url.split('?')[1]
  const urlSearchParams = new URLSearchParams(urlStr)
  return Object.fromEntries(urlSearchParams.entries())
}
