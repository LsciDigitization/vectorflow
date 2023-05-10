// 转换服务器端的组件路径到本地组件路径
const remoteComponentPaths = {
  'system/user/SysUserIndex' : 'system/org/user/index',
  'system/dept/SysDeptIndex' : 'system/org/dept/index',
  'system/post/PostIndex' : 'system/org/post/index',

  'system/role/QueryList' : 'system/auth/role/index',
  // 'system/role/SysRoleAuth' : '',

  'system/menu/MenuIndex' : 'system/setting/menu/index',
  'system/dict/DictIndex' : 'system/setting/dict/index',
  'system/config/ConfigIndex' : 'system/setting/config/index',

  'monitor/operlog/OperlogIndex' : 'system/log/operationLog/index',
  'monitor/loginlog/LoginLogIndex' : 'system/log/loginLog/index',
  'monitor/job/log' : 'system/log/monitor/jobLog',

  // 'system/notice/NoticeIndex' : 'system/notice',
  // 'system/sysportlet/index' : '',
  // 'system/sysportalconfig/index' : '',

  'monitor/job/index' : 'system/scheduling/job/index',

  'monitor/online/index' : 'system/monitor/online',
  // 'monitor/druid/index' : 'system/monitor/druid',
  'monitor/server/index' : 'system/monitor/server',
  'monitor/cache/index' : 'system/monitor/cache',
  'monitor/cache/indexCacheList' : 'system/monitor/cacheList',

};

const remoteRouterPaths = {
  // '/system/org/user' : '/system/user',
  // '/system/org/dept' : '/system/dept',
  // '/system/org/post' : '/system/post',

  // '/system/auth/role' : '/system/role',
  // '/system/auth/sysAuth' : '',

  '/system/sysSetting/menu' : '/system/setting/menu',
  '/system/sysSetting/dict' : '/system/setting/dict',
  '/system/sysSetting/config' : '/system/setting/config',

  '/system/log/operlog' : '/system/log/operationLog',
  '/system/log/loginLog' : '/system/log/loginLog',
  '/system/log/joblog' : '/system/log/jobLog',

  // '/system/sysApp/notice' : '/system/notice',
  // '/system/sysApp/sysPortlet' : '',
  // '/system/sysApp/sysPortalConfig' : '',


  '/system/monitor/job' : '/system/scheduling/job',

  '/system/monitor/online' : '/monitor/online',
  // '/system/monitor/druid' : '/monitor/druid',
  '/system/monitor/server' : '/monitor/server',
  '/system/monitor/cache' : '/monitor/cache',
  '/system/monitor/cacheList' : '/monitor/cacheList',

};

export function getLocalComponentPath(path: string): string {
  if (remoteComponentPaths.hasOwnProperty(path)) {
    return remoteComponentPaths[path];
  } else {
    return path;
  }
}

export function getLocalRouterPath(path: string): string {
  if (remoteRouterPaths.hasOwnProperty(path)) {
    return remoteRouterPaths[path];
  } else {
    return path;
  }
}

