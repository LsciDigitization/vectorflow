import { createIcon } from '@/utils/IconUtil';
import request from '@/utils/request'
import type { MenuDataItem } from '@umijs/route-utils';
// import menus from "@/services/system/menus";
import {getResponseData} from "@/services/api/ApiResponse";
import {getLocalComponentPath} from "@/services/system/remoteComponent"
import menus from "@/services/system/menus";

/** 获取当前的用户 GET /getUserInfo */
export async function getUserInfo (options?: Record<string, any>) {
  return request<API.GetUserInfoResult>('/getInfo', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /login/outLogin */
export async function logout (options?: Record<string, any>) {
  return request<Record<string, any>>('/logout', {
    method: 'POST',
    ...(options || {}),
  });
}


export async function getRouters(): Promise<API.ApiResponse> {
  return request('/getRouters');
}

export async function getRoutersWithData(): Promise<API.ResRouterMenuItem[]> {
  return getResponseData(await request('/getRouters'));
}

export async function getRoutersInfo(): Promise<MenuDataItem[]> {
  // return getRoutersWithData().then((data) => {
  //   return convertCompatRouters(data);
  //   // return menus
  // });
  return menus
}



function convertCompatRouters(childrens: API.ResRouterMenuItem[]): MenuDataItem[] {
  // console.log(childrens)
  return childrens.map((item: API.ResRouterMenuItem) => {
    // console.log(item)
    return {
      path: item.path,
      icon: createIcon(item.meta.icon),
      name: item.meta.title,
      routes: item.children ? convertCompatRouters(item.children) : undefined,
      hideChildrenInMenu: item.hidden,
      hideInMenu: item.hidden,
      component: getLocalComponentPath(item.component),
      // authority: item.perms,
      KeepAlive: true,
    };
  });
}

export function getMatchMenuItem(path: string, menuData: MenuDataItem[]|undefined): MenuDataItem[] {
  if(!menuData)
    return [];
  let items: MenuDataItem[] = [];
  menuData.forEach((item) => {
    if (item.path) {
      if (item.path === path) {
        items.push(item);
        return;
      }
      if (path.length >= item.path?.length) {
        const exp = `${item.path}/*`;
        if (path.match(exp)) {
          if(item.routes) {
            const subpath = path.substr(item.path.length+1);
            const subItem: MenuDataItem[] = getMatchMenuItem(subpath, item.routes);
            items = items.concat(subItem);
          } else {
            const paths = path.split('/');
            if(paths.length >= 2 && paths[0] === item.path && paths[1] === 'index') {
              items.push(item);
            }
          }
        }
      }
    }
  });
  return items;
}

