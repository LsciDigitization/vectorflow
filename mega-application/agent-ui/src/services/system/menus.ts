

// 仪表盘 任务动态 设备动态 板池动态 开关机动态 实例 甘特图

const menus = [
  {
    path: '/dashboard',
    name: '仪表盘',
    icon: '',
    component: '@/layouts/TabsLayout',
    routes: [
      {path: '/dashboard', redirect: '/dashboard/analysis'},

      ]
  },
  {
    path: '/dynamic/instanceTask',
    name: '任务动态',
    icon: '',
    component: 'hephaestus/dynamic/instanceTask/index',
  },
  {
    path: '/dynamic/deviceTask',
    name: '设备动态',
    icon: '',
    component: 'hephaestus/dynamic/deviceTask/index',
  },
  {
    path: '/dynamic/instancePlate',
    name: '板池动态',
    icon: '',
    component: 'hephaestus/dynamic/instancePlate/index',
  },
  {
    path: '/dynamic/iteration',
    name: '通量动态',
    icon: '',
    component: 'hephaestus/dynamic/instanceIteration/index',
  },
  {
    path: '/dynamic/instanceDynamic',
    name: '流程动态',
    icon: '',
    component: 'hephaestus/experiment/group/experimentHistory/instanceDynamic/index',
  },
  {
    path: '/dynamic/gantt',
    name: '甘特图',
    icon: '',
    component: 'hephaestus/experiment/group/experimentHistory/gantt/myGantt',
  },
  {
    path: '/dynamic/storage',
    name: '仓储',
    icon: '',
    component: 'hephaestus/inventory/location/index',
  },
  {
    path: '/work/flow',
    name: '工作流',
    icon: '',
    component: 'hephaestus/flow/index',
  },
  {
    path: '/plate/transfer',
    name: '移液',
    icon: '',
    component: 'hephaestus/plate/transfer/index',
    routes: [
      {
        path: "/plate/transfer/items",
        icon: "",
        name: "移液记录",
        hideChildrenInMenu: false,
        hideInMenu: false,
        component: "hephaestus/plate/transfer/index",
        KeepAlive: true
      },

      {
        path: "/plate/transferPlan/items",
        icon: "",
        name: "移液计划",
        hideChildrenInMenu: false,
        hideInMenu: false,
        component: "hephaestus/plate/transferPlan/index",
        KeepAlive: true
      },
      {
        path: "/plate/pipette/items",
        icon: "",
        name: "枪头列表",
        hideChildrenInMenu: false,
        hideInMenu: false,
        component: "hephaestus/plate/pipette/index",
        KeepAlive: true
      },
      {
        path: "/plate/liquid/items",
        icon: "",
        name: "液体列表",
        hideChildrenInMenu: false,
        hideInMenu: false,
        component: "hephaestus/plate/liquid/index",
        KeepAlive: true
      },
      {
        path: "/plate/sample/items",
        icon: "",
        name: "样品列表",
        hideChildrenInMenu: false,
        hideInMenu: false,
        component: "hephaestus/plate/sample/index",
        KeepAlive: true
      }

      ]
  },

];

export default menus;
