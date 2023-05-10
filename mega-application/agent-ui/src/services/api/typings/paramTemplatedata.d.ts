declare namespace API {
  // 模板管理列表
  // api/pmsTemplate/list
  type PmsTemplateList = {
    deviceType: any;
    commandParameter: any;
    description: any;
    updateTime: any;
    updateName: any;
    deviceId: string;
    deviceCommand: string;
    createBy: number;
    isDeleted: string;
    createTime: string;
    updateBy: any;
    templateName: string;
    id: number;
    createName: any;
    remarks: any;
  }

  // 标签管理
  // api/pmsTag/list
  type PmsTagList = {
    createBy: number;
    isDeleted: string;
    createTime: string;
    updateBy: any;
    updateTime: any;
    id: number;
    tagName: string;
    updateName: any;
    createName: any;
    remarks: any;
    tagDescription: any;
  }

}
