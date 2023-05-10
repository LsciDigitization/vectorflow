/**
 * 路由菜单类型
 */
declare namespace API {
  type ResRouterMenuItem = {
    redirect: string;
    path: string;
    component: string;
    hidden: boolean;
    children: ResRouterMenuItem[];
    meta: MenuMetaItem;
    name: string;
    isFrame: string;
    alwaysShow: boolean;
  }

  type MenuMetaItem = {
    noCache: boolean;
    icon: string;
    remark: any;
    title: string;
  }
}

