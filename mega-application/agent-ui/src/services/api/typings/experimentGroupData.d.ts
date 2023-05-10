declare namespace API {
  // 资源编排
  // api/experimentGroup/list
  type ExperimentGroupList = {
    experimentRule: string;
    updateTime: string;
    updateName: any;
    createBy: number;
    isDeleted: string;
    createTime: string;
    updateBy: number;
    experimentGroupName: string;
    experimentGroupInitStatus: number;
    experimentGroupDescription: string;
    id: number;
    createName: any;
    remarks: any;
  }

  // 运行记录
  // api/experimentGroupHistory/list
  type ExperimentGroupHistoryList = {
    groupId: number;
    updateTime: any;
    groupStatus: number;
    updateName: any;
    groupStatusName: string;
    createBy: number;
    groupName: string;
    groupContext: any;
    isDeleted: string;
    createTime: string;
    updateBy: any;
    id: number;
    createName: any;
    remarks: any;
  }

}
