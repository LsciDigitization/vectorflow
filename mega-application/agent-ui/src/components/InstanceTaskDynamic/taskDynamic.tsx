import  {ReactNode,} from "react";
import ProTable, {ProColumns} from "@ant-design/pro-table";
import {Tag} from "antd";
import {Instance, InstanceDynamic, TaskItem} from "@/components/InstanceTaskDynamic/data";



const formatTaskName = (item: TaskItem) => {
  if (!item.virtual) {
    if (item.stepKey) {
      return 'NO.' + item.taskNo + '（' + item.stepKey + '）'
    } else {
      return 'NO.' + item.taskNo
    }
  }
  return '';
};
const formatTableTitle = (instanceId: string, durationTotalFormat: number) => {

  return "(" + instanceId + "）流程动态" + "    总耗时：" + durationTotalFormat

}

const formatColor = (item: TaskItem) => {
  if (!item.virtual) {

    if (item.taskStatus === 'finished' || item.taskStatus === 'failed') {

      if (item.deviceKey.indexOf("LabwareHandler") > -1) {
        return "cyan";
      } else {
        if (item.taskStatus === 'finished') {
          return 'blue';
        } else if (item.taskStatus === 'failed') {
          return 'red';
        } else {
          return '';
        }
      }
    } else {
      if (item.taskStatus === 'running') {
        return 'green';
      } else {
        return '';
      }
    }
  } else {
    return '';
  }

};

const columnsInstance: ProColumns<InstanceDynamic>[] = [
  {
    title: '流程过程',
    dataIndex: 'taskList',
    valueType: 'text',
    filterSearch: false,
    search: false,
    render: (text: ReactNode, record: InstanceDynamic) => {
      console.log('InstanceDynamic',record)
      return <div style={{display: 'flex', height: '300px'}}>
        {
          record.taskList && record.taskList.map((item: TaskItem) => (
              <div>
                <Tag color={formatColor(item)} style={{height: '60px', width: '150px'}}>
                  {
                    !item.virtual && (
                      <div>
                        <div>{formatTaskName(item)}</div>
                        <div>{item.deviceKey}</div>
                        <div>{'运行 ' + item.runDuration + ' 秒'}</div>
                      </div>
                    )
                  }
                  {
                    item.virtual && (
                      <div>
                        <div>&nbsp;</div>
                        <div>&nbsp;</div>
                        <div>{'等待 ' + item.waitDuration + ' 秒'}</div>
                      </div>
                    )
                  }
                </Tag>

                <div>
                  {
                    !item.virtual && (
                      <div>
                        <div>{item.startTimeFormatted}</div>
                        <div>{item.endTimeFormatted}</div>
                      </div>
                    )
                  }
                  {
                    item.virtual && (
                      <div>{item.taskName}</div>
                    )
                  }
                </div>
              </div>
            )
          )
        }
      </div>
    },
  },

];

const InstanceTaskDynamic = (props: Instance) => {

  if(props && props.InstanceDynamics && props.InstanceDynamics.length >0){
    let title = ''
    if(!props.title){
      const instanceId = props.InstanceDynamics[0].instanceId + ''
      const durationTotalFormat = props.InstanceDynamics[0].durationTotalFormat
      title = formatTableTitle(instanceId,durationTotalFormat)
    }else{
      title = props.title
    }

    return (
      <div>
        <div>
          <div>
            <Tag color="blue">
              &nbsp; &nbsp; &nbsp; &nbsp;
            </Tag>
            <span>已完成</span>

            <Tag color="green" style={{marginLeft: '12px'}}>
              &nbsp; &nbsp; &nbsp; &nbsp;
            </Tag>
            <span>进行中</span>

            <Tag color="red" style={{marginLeft: '12px'}}>
              &nbsp; &nbsp; &nbsp; &nbsp;
            </Tag>
            <span>执行失败</span>

            <Tag color="" style={{marginLeft: '12px'}}>
              &nbsp; &nbsp; &nbsp; &nbsp;
            </Tag>
            <span>等待中</span>

            <Tag color="cyan" style={{marginLeft: '12px'}}>
              &nbsp; &nbsp; &nbsp; &nbsp;
            </Tag>
            <span>机械臂</span>
          </div>
        </div>

        <ProTable
          headerTitle={title}
          rowKey="id"
          pagination={false}
          search={false}
          options={false}
          showHeader={true}
          scroll={{x: 1500}}
          dataSource={props.InstanceDynamics}
          style={{marginTop: "12px"}}
          // dataSource={[]}
          columns={columnsInstance}
        />
      </div>
    )

  }else{

    return  <div/>
  }


};

export default InstanceTaskDynamic;
