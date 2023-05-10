export default {
  '/api/auth_routes': {
    '/form/advanced-form': { authority: ['admin', 'user'] },
  },

  '/getRouters': {
    "msg": "操作成功",
    "code": 200,
    "data": [
      {
        "name": "Device",
        "path": "/device",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "设备管理",
          "icon": "build",
          "noCache": false,
          "remark": null
        },
        "children": [
          {
            "name": "Device",
            "path": "device",
            "hidden": false,
            "component": "hephaestus/device/index",
            "isFrame": "1",
            "meta": {
              "title": "设备管理",
              "icon": "device",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Pmslog",
            "path": "pmslog",
            "hidden": true,
            "component": "hephaestus/pmsTraceLog/index",
            "isFrame": "1",
            "meta": {
              "title": "指令记录",
              "icon": "textarea",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Lock",
            "path": "lock",
            "hidden": true,
            "component": "hephaestus/lock/index",
            "isFrame": "1",
            "meta": {
              "title": "设备锁管理",
              "icon": "unlock",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Storage",
            "path": "storage",
            "hidden": false,
            "component": "hephaestus/storage/index",
            "isFrame": "1",
            "meta": {
              "title": "设备存储池",
              "icon": "druid",
              "noCache": false,
              "remark": null
            }
          }
        ]
      },
      {
        "name": "Demo",
        "path": "/demo",
        "hidden": true,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "示例中心",
          "icon": "pie-chart",
          "noCache": false,
          "remark": ""
        },
        "children": [
          {
            "name": "Echart",
            "path": "echart",
            "hidden": false,
            "component": "demo/chart/echartDashBoard",
            "isFrame": "1",
            "meta": {
              "title": "报表看板",
              "icon": "monitor",
              "noCache": false,
              "remark": ""
            }
          },
          {
            "name": "SelectUser",
            "path": "selectUser",
            "hidden": false,
            "component": "demo/selectUser/selectUser",
            "isFrame": "1",
            "meta": {
              "title": "选人页",
              "icon": "peoples",
              "noCache": false,
              "remark": ""
            }
          },
          {
            "name": "SelectDept",
            "path": "selectDept",
            "hidden": false,
            "component": "demo/selectDept/selectDept",
            "isFrame": "1",
            "meta": {
              "title": "选部门",
              "icon": "tree",
              "noCache": false,
              "remark": ""
            }
          },
          {
            "name": "Http://localhost:8000/",
            "path": "http://localhost:8000/",
            "hidden": false,
            "component": "Layout",
            "isFrame": "0",
            "meta": {
              "title": "测试外链",
              "icon": "compress",
              "noCache": false,
              "remark": ""
            }
          }
        ]
      },
      {
        "name": "Vuedata",
        "path": "/vuedata",
        "hidden": true,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "可视化大屏",
          "icon": "dashboardNew",
          "noCache": false,
          "remark": ""
        },
        "children": [
          {
            "name": " http://localhost:8081/",
            "path": " http://localhost:8081/",
            "hidden": false,
            "component": "Layout",
            "isFrame": "0",
            "meta": {
              "title": "可视化大屏",
              "icon": "monitor",
              "noCache": false,
              "remark": ""
            }
          }
        ]
      },
      {
        "name": "Pms",
        "path": "/pms",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "参数模板",
          "icon": "swagger",
          "noCache": false,
          "remark": null
        },
        "children": [
          {
            "name": "PmsTemplate",
            "path": "pmsTemplate",
            "hidden": false,
            "component": "hephaestus/pmstemplate/index",
            "isFrame": "1",
            "meta": {
              "title": "模板管理",
              "icon": null,
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Tag",
            "path": "tag",
            "hidden": false,
            "component": "hephaestus/pmstag/index",
            "isFrame": "1",
            "meta": {
              "title": "标签管理",
              "icon": null,
              "noCache": false,
              "remark": null
            }
          }
        ]
      },
      {
        "name": "Hephaestus",
        "path": "/hephaestus",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "实验管理",
          "icon": "log",
          "noCache": false,
          "remark": null
        },
        "children": [
          {
            "name": "Experiment",
            "path": "experiment",
            "hidden": false,
            "component": "hephaestus/experiment/index",
            "isFrame": "1",
            "meta": {
              "title": "实验管理",
              "icon": "monitor",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Stage",
            "path": "stage",
            "hidden": true,
            "component": "hephaestus/stage/index",
            "isFrame": "1",
            "meta": {
              "title": "实验阶段管理",
              "icon": "companyFill",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "StageTask",
            "path": "stageTask",
            "hidden": true,
            "component": "hephaestus/stageTask/index",
            "isFrame": "1",
            "meta": {
              "title": "任务管理",
              "icon": "component",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Instance",
            "path": "instance",
            "hidden": false,
            "component": "hephaestus/instance/index",
            "isFrame": "1",
            "meta": {
              "title": "实例管理",
              "icon": "cascader",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Gantt",
            "path": "gantt",
            "hidden": true,
            "component": "hephaestus/gantt/index",
            "isFrame": "1",
            "meta": {
              "title": "甘特图",
              "icon": "job",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "InstanceLog",
            "path": "instanceLog",
            "hidden": true,
            "component": "hephaestus/instanceLog/index",
            "isFrame": "1",
            "meta": {
              "title": "执行过程",
              "icon": "color",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "InstanceDynamic",
            "path": "instanceDynamic",
            "hidden": false,
            "component": "hephaestus/instanceDynamic/index",
            "isFrame": "1",
            "meta": {
              "title": "实例动态",
              "icon": "build",
              "noCache": false,
              "remark": null
            }
          }
        ]
      },
      {
        "name": "Group",
        "path": "/group",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "实验组管理",
          "icon": "appstore",
          "noCache": false,
          "remark": null
        },
        "children": [
          {
            "name": "Index",
            "path": "index",
            "hidden": false,
            "component": "hephaestus/experimentGroup/index",
            "isFrame": "1",
            "meta": {
              "title": "资源编排",
              "icon": "credit-card",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "History",
            "path": "history",
            "hidden": false,
            "component": "hephaestus/experimentGroupHistory/index",
            "isFrame": "1",
            "meta": {
              "title": "运行记录",
              "icon": "dashboardNew",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "DeviceLock",
            "path": "deviceLock",
            "hidden": true,
            "component": "hephaestus/lock/deviceLock",
            "isFrame": "1",
            "meta": {
              "title": "任务仪表盘",
              "icon": "lock",
              "noCache": false,
              "remark": null
            }
          }
        ]
      },
      {
        "name": "Dynamic",
        "path": "/dynamic",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "运行动态",
          "icon": "log",
          "noCache": false,
          "remark": null
        },
        "children": [
          {
            "name": "InstanceStep",
            "path": "instanceStep",
            "hidden": true,
            "component": "hephaestus/instanceStep/index",
            "isFrame": "1",
            "meta": {
              "title": "实例步骤",
              "icon": "swagger",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "InstanceTask",
            "path": "instanceTask",
            "hidden": false,
            "component": "hephaestus/instanceTask/index",
            "isFrame": "1",
            "meta": {
              "title": "任务动态",
              "icon": "date",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "DeivceTask",
            "path": "deivceTask",
            "hidden": false,
            "component": "hephaestus/deviceTask/deviceTaskDynamic",
            "isFrame": "1",
            "meta": {
              "title": "设备动态",
              "icon": "QRcode",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "InstancePlate",
            "path": "instancePlate",
            "hidden": false,
            "component": "hephaestus/instancePlate/indexDynamic",
            "isFrame": "1",
            "meta": {
              "title": "板池动态",
              "icon": "documentation",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "IndexCapDynamic",
            "path": "indexCapDynamic",
            "hidden": false,
            "component": "hephaestus/cap/indexCapDynamic",
            "isFrame": "1",
            "meta": {
              "title": "开盖机动态",
              "icon": "row",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "Hephaestus/deviceTask/index",
            "path": "hephaestus/deviceTask/index",
            "hidden": false,
            "component": "hephaestus/deviceTask/index",
            "isFrame": "1",
            "meta": {
              "title": "设备任务",
              "icon": "Sina",
              "noCache": false,
              "remark": null
            }
          },
          {
            "name": "DeviceTaskDashboard",
            "path": "deviceTaskDashboard",
            "hidden": false,
            "component": "hephaestus/deviceTask/deviceTaskDashboard",
            "isFrame": "1",
            "meta": {
              "title": "任务大盘",
              "icon": "dingtalk",
              "noCache": false,
              "remark": null
            }
          }
        ]
      },
      {
        "name": "System",
        "path": "/system",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "系统管理",
          "icon": "setting",
          "noCache": false,
          "remark": "系统管理目录"
        },
        "children": [
          {
            "name": "Org",
            "path": "org",
            "hidden": false,
            "redirect": "noRedirect",
            "component": "ParentView",
            "alwaysShow": true,
            "isFrame": "1",
            "meta": {
              "title": "组织管理",
              "icon": "appstore",
              "noCache": false,
              "remark": ""
            },
            "children": [
              {
                "name": "User",
                "path": "user",
                "hidden": false,
                "component": "system/user/SysUserIndex",
                "isFrame": "1",
                "meta": {
                  "title": "用户管理",
                  "icon": "team",
                  "noCache": false,
                  "remark": "主要维护平台相关用户信息"
                }
              },
              {
                "name": "Dept",
                "path": "dept",
                "hidden": false,
                "component": "system/dept/SysDeptIndex",
                "isFrame": "1",
                "meta": {
                  "title": "部门管理",
                  "icon": "cluster",
                  "noCache": false,
                  "remark": "部门管理菜单"
                }
              },
              {
                "name": "Post",
                "path": "post",
                "hidden": false,
                "component": "system/post/PostIndex",
                "isFrame": "1",
                "meta": {
                  "title": "岗位管理",
                  "icon": "idcard",
                  "noCache": false,
                  "remark": "岗位管理菜单"
                }
              }
            ]
          },
          {
            "name": "Auth",
            "path": "auth",
            "hidden": false,
            "redirect": "noRedirect",
            "component": "ParentView",
            "alwaysShow": true,
            "isFrame": "1",
            "meta": {
              "title": "权限管理",
              "icon": "solution",
              "noCache": false,
              "remark": ""
            },
            "children": [
              {
                "name": "Role",
                "path": "role",
                "hidden": false,
                "component": "system/role/QueryList",
                "isFrame": "1",
                "meta": {
                  "title": "角色管理",
                  "icon": "contacts",
                  "noCache": false,
                  "remark": "维护平台各角色数据以及权限分配."
                }
              },
              {
                "name": "SysAuth",
                "path": "sysAuth",
                "hidden": false,
                "component": "system/role/SysRoleAuth",
                "isFrame": "1",
                "meta": {
                  "title": "菜单授权",
                  "icon": "api",
                  "noCache": false,
                  "remark": ""
                }
              }
            ]
          },
          {
            "name": "SysSetting",
            "path": "sysSetting",
            "hidden": false,
            "redirect": "noRedirect",
            "component": "ParentView",
            "alwaysShow": true,
            "isFrame": "1",
            "meta": {
              "title": "系统设置",
              "icon": "setting",
              "noCache": false,
              "remark": ""
            },
            "children": [
              {
                "name": "Menu",
                "path": "menu",
                "hidden": false,
                "component": "system/menu/MenuIndex",
                "isFrame": "1",
                "meta": {
                  "title": "菜单管理",
                  "icon": "bars",
                  "noCache": false,
                  "remark": "平台所有菜单维护"
                }
              },
              {
                "name": "Dict",
                "path": "dict",
                "hidden": false,
                "component": "system/dict/DictIndex",
                "isFrame": "1",
                "meta": {
                  "title": "字典管理",
                  "icon": "read",
                  "noCache": false,
                  "remark": "字典管理菜单"
                }
              },
              {
                "name": "Config",
                "path": "config",
                "hidden": false,
                "component": "system/config/ConfigIndex",
                "isFrame": "1",
                "meta": {
                  "title": "参数设置",
                  "icon": "code",
                  "noCache": false,
                  "remark": "参数设置菜单"
                }
              }
            ]
          },
          {
            "name": "Log",
            "path": "log",
            "hidden": false,
            "redirect": "noRedirect",
            "component": "ParentView",
            "alwaysShow": true,
            "isFrame": "1",
            "meta": {
              "title": "日志管理",
              "icon": "copy",
              "noCache": false,
              "remark": "日志管理菜单"
            },
            "children": [
              {
                "name": "Operlog",
                "path": "operlog",
                "hidden": false,
                "component": "monitor/operlog/OperlogIndex",
                "isFrame": "1",
                "meta": {
                  "title": "操作日志",
                  "icon": "form",
                  "noCache": false,
                  "remark": "操作日志菜单"
                }
              },
              {
                "name": "LoginLog",
                "path": "loginLog",
                "hidden": false,
                "component": "monitor/loginlog/LoginLogIndex",
                "isFrame": "1",
                "meta": {
                  "title": "登录日志",
                  "icon": "loginLog",
                  "noCache": false,
                  "remark": "登录日志菜单"
                }
              },
              {
                "name": "Joblog",
                "path": "joblog",
                "hidden": false,
                "component": "monitor/job/log",
                "isFrame": "1",
                "meta": {
                  "title": "调度日志",
                  "icon": "bug",
                  "noCache": false,
                  "remark": ""
                }
              }
            ]
          },
          {
            "name": "SysApp",
            "path": "sysApp",
            "hidden": false,
            "redirect": "noRedirect",
            "component": "ParentView",
            "alwaysShow": true,
            "isFrame": "1",
            "meta": {
              "title": "系统应用",
              "icon": "laptop",
              "noCache": false,
              "remark": ""
            },
            "children": [
              {
                "name": "Notice",
                "path": "notice",
                "hidden": false,
                "component": "system/notice/NoticeIndex",
                "isFrame": "1",
                "meta": {
                  "title": "通知公告",
                  "icon": "notification",
                  "noCache": false,
                  "remark": "通知公告菜单"
                }
              },
              {
                "name": "SysPortlet",
                "path": "sysPortlet",
                "hidden": false,
                "component": "system/sysportlet/index",
                "isFrame": "1",
                "meta": {
                  "title": "工作台小页",
                  "icon": "color",
                  "noCache": false,
                  "remark": ""
                }
              },
              {
                "name": "SysPortalConfig",
                "path": "sysPortalConfig",
                "hidden": false,
                "component": "system/sysportalconfig/index",
                "isFrame": "1",
                "meta": {
                  "title": "工作台配置",
                  "icon": "dict",
                  "noCache": false,
                  "remark": ""
                }
              }
            ]
          }
        ]
      },
      {
        "name": "Monitor",
        "path": "/monitor",
        "hidden": false,
        "redirect": "noRedirect",
        "component": "Layout",
        "alwaysShow": true,
        "isFrame": "1",
        "meta": {
          "title": "系统监控",
          "icon": "fund",
          "noCache": false,
          "remark": "系统监控目录"
        },
        "children": [
          {
            "name": "Online",
            "path": "online",
            "hidden": false,
            "component": "monitor/online/index",
            "isFrame": "1",
            "meta": {
              "title": "在线用户",
              "icon": "dot-chart",
              "noCache": false,
              "remark": "在线用户菜单"
            }
          },
          {
            "name": "Job",
            "path": "job",
            "hidden": false,
            "component": "monitor/job/index",
            "isFrame": "1",
            "meta": {
              "title": "定时任务",
              "icon": "bar-chart",
              "noCache": false,
              "remark": "定时任务菜单"
            }
          },
          {
            "name": "Druid",
            "path": "druid",
            "hidden": false,
            "component": "monitor/druid/index",
            "isFrame": "1",
            "meta": {
              "title": "数据监控",
              "icon": "dashboard",
              "noCache": false,
              "remark": "数据监控菜单"
            }
          },
          {
            "name": "Server",
            "path": "server",
            "hidden": false,
            "component": "monitor/server/index",
            "isFrame": "1",
            "meta": {
              "title": "服务监控",
              "icon": "pie-chart",
              "noCache": false,
              "remark": "服务监控菜单"
            }
          },
          {
            "name": "Cache",
            "path": "cache",
            "hidden": false,
            "component": "monitor/cache/index",
            "isFrame": "1",
            "meta": {
              "title": "缓存监控",
              "icon": "box-plot",
              "noCache": false,
              "remark": "缓存监控菜单"
            }
          },
          {
            "name": "CacheList",
            "path": "cacheList",
            "hidden": false,
            "component": "monitor/cache/indexCacheList",
            "isFrame": "1",
            "meta": {
              "title": "缓存列表",
              "icon": "dashboardNew",
              "noCache": false,
              "remark": null
            }
          }
        ]
      }
    ]
  },


};
