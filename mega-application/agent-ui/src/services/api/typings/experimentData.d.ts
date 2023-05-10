declare namespace API {
  // 实验管理
  // api/experiment/list
  type ExperimentList = {
    experimentDescription: string;
    createBy: number;
    isDeleted: string;
    priorityLevel: number;
    createTime: string;
    updateBy: number;
    updateTime: string;
    id: number;
    updateName: any;
    createName: any;
    remarks: any;
    experimentName: string;
  }

  // 实例管理
  // api/instance/list
  type InstanceList = {
    instanceContext: string;
    instanceEndTime: any;
    updateTime: string;
    activeStageId: any;
    activeStageStatus: string;
    instanceStartTime: string;
    updateName: any;
    instanceStatus: string;
    createBy: number;
    instanceId: any;
    isDeleted: string;
    createTime: string;
    updateBy: number;
    instanceTitle: string;
    id: number;
    createName: any;
    remarks: any;
  }


  // 实例动态
  // api/instance/instanceDynamicList
  type InstanceDynamicList = {
    stageTaskList: stageTaskListItem[];
    instanceTitle: string;
  }

  type stageTaskListItem = {
    stageName: string;
    stageTaskName: string;
    taskStatus: string;
    taskStartTime: string;
  }

}

