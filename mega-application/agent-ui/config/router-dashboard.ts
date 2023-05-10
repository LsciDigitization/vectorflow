const routerDashboard = {
  path: '/dashboard',
  name: 'Dashboard',
  icon: 'dashboard',
  component: '@/layouts/TabsLayout',
  routes: [
    {path: '/dashboard', redirect: '/dashboard/analysis'},
    {
      name: '仪表盘',
      icon: 'smile',
      path: '/dashboard/analysis',
      component: './dashboard/analysis',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '仪表盘',
    },
    {
      name: '监控页',
      icon: 'smile',
      path: '/dashboard/monitor',
      component: './dashboard/monitor',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '监控页',
    },
    {
      name: '工作台',
      icon: 'smile',
      path: '/dashboard/workplace',
      component: './dashboard/workplace',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '工作台',
    },
  ],
};


export   {
  routerDashboard
};
