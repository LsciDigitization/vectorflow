import {routerDashboard} from "./router-dashboard";
import { routerInstance,routerPlate,routerWorkFlow,routerInventory} from "./router-hephaestus";

export default [
  {path: '/', component: './dashboard/analysis',exact: true, },
  routerDashboard,
  routerInstance,
  routerWorkFlow,
  routerPlate,
  routerInventory,
  // 首页路由

  // {path: '/test', component: './test/Test'},
  // {path: '/test-icon', component: './test/TestIcon'},
  {component: './404'},
];
