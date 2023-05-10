// 资源
const routerDevice = {
  icon: 'BugResource',
  path: '/resource',
  component: '@/layouts/TabsLayout',
  routes: [
    {path: '/', redirect: '/experiment/device/device'},

  ],
}


// 动态
const routerInstance = {
  icon: 'BugDynamic',
  path: '/dynamic',
  component: '@/layouts/TabsLayout',
  routes: [
    {path: '/', redirect: '/dynamic/instanceTask'},
    {
      icon: 'instance',
      path: '/dynamic/instanceTask',
      component: 'hephaestus/dynamic/instanceTask/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '任务动态',
    },
    {
      icon: 'instance',
      path: '/dynamic/deviceTask',
      component: 'hephaestus/dynamic/deviceTask/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '设备动态',
    },
    {
      icon: 'instance',
      path: '/dynamic/iteration',
      component: 'hephaestus/dynamic/instanceIteration/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '通量动态',
    },
    {
      icon: 'instance',
      path: '/dynamic/instancePlate',
      component: 'hephaestus/dynamic/instancePlate/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '板池动态',
    },
    {
      icon: 'instance',
      path: '/dynamic/gantt',
      component: 'hephaestus/experiment/group/experimentHistory/gantt/myGantt',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '甘特图',
    },
    {
      icon: 'instance',
      path: '/dynamic/instanceDynamic',
      component: 'hephaestus/experiment/group/experimentHistory/instanceDynamic/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '流程动态',
    },
    {
      icon: 'instance',
      path: '/dynamic/storage',
      component: 'hephaestus/inventory/location/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '仓储',
    }
  ],
};

/**
 * 甘特图路由
 */
const routerGantt = {
  path: '/dynamic/gantt',
  component: 'hephaestus/experiment/group/experimentHistory/gantt/myGantt',
  KeepAlive: true,
  title: '甘特图'
}

/**
 * 实例动态
 */
const routerInstanceDynamic = {
  path: '/dynamic/instanceDynamic',
  component: 'hephaestus/experiment/group/experimentHistory/instanceDynamic/index',
  KeepAlive: true,
  title: '实例动态'
}


const routerPlate = {
  icon: 'BugResource',
  // path: '/plate/transfer',
  component: '@/layouts/TabsLayout',
  // redirect: '/plate/transfer/items',
  routes: [
    {path:'/plate/transfer',redirect: '/plate/transfer/items'},
    {
      icon: 'experiment',
      path: '/plate/transfer/items',
      component: 'hephaestus/plate/transfer/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '移液记录',
    },
    {
      icon: 'experiment',
      path: '/plate/transferPlan/items',
      component: 'hephaestus/plate/transferPlan/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '移液计划',
    },
    {
      icon: 'experiment',
      path: '/plate/pipette/items',
      component: 'hephaestus/plate/pipette/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '枪头',
    },
    {
      icon: 'experiment',
      path: '/plate/liquid/items',
      component: 'hephaestus/plate/liquid/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '液体',
    },
    {
      icon: 'experiment',
      path: '/plate/sample/items',
      component: 'hephaestus/plate/sample/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '样品',
    },

  ],
}

const routerWorkFlow = {
  icon: 'BugResource',
  path: '/work',
  component: '@/layouts/TabsLayout',
  routes: [
    {path: '/work', redirect: '/work/flow'},
    {
      icon: 'experiment',
      path: '/work/flow',
      component: 'hephaestus/flow/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '工作流',
    },
  ],
}

const routerInventory = {
  icon: 'BugDynamic',
  path: '/abc',
  component: '@/layouts/TabsLayout',
  routes: [
    {path: '/', redirect: '/abc/inventory'},

    {
      icon: 'instance',
      path: '/abc/storage',
      component: 'hephaestus/inventory/location/index',
      access: 'authorize',
      wrappers: ['@/components/KeepAlive'],
      KeepAlive: true,
      title: '资源存储',
    }
  ],
};

export {
  routerDevice,

  routerInstance,
  routerGantt,
  routerInstanceDynamic,
  routerPlate,
  routerWorkFlow,
  routerInventory
}
