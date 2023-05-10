// @ts-ignore
import type {ReactNode} from 'react';
import React, {useEffect, useState} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import "./instanceDynamic.css"
// @ts-ignore
import WrapContent from '@/components/WrapContent';
import {randomUUID} from "@/utils/utils";
import {getUnFinishedInstanceTask} from "@/pages/hephaestus/experiment/group/experimentHistory/instanceDynamic/service";
import {Button, Form, Tag} from "antd";
import ProTable from "@ant-design/pro-table";
import {InstanceDynamic, TaskItem} from "@/components/InstanceTaskDynamic/data";
import Card from "antd/lib/card/Card";

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

const formatPoolTypeColor = (item: InstanceDynamic) => {
  if (item.poolType === '样品') {
    return 'green'
  }
  if (item.poolType === '标品') {
    return 'yellow'
  }
  if (item.poolType === '空板') {
    return ''
  }
  return ''
};

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

const print = () => {
  const style = document.createElement("style")
  const width = (document.getElementsByClassName("ant-table-tbody")[0].scrollWidth + 500) + 'px'
  const heigth = (document.getElementsByClassName("ant-table-tbody")[0].scrollHeight + 500) + 'px'
  style.innerHTML = "@page{size: " + width + " " + heigth + ";margin: 3.7cm 2.6cm 3.5cm;}"
  window.document.head.appendChild(style);
  window.print()
  window.document.head.removeChild(style)

}

const columnsInstance: ProColumns<InstanceDynamic>[] = [
  {
    title: '流程名称',
    dataIndex: 'instanceTitle',
    valueType: 'text',
    filterSearch: false,
    width: 250,
    search: false,
    render: (text: ReactNode, record: InstanceDynamic) => {
      return <div style={{display: 'flex', height: '150px',width:'200px'}}>
        {
          <div>
            <Tag color={formatPoolTypeColor(record)}>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {record.poolType}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </Tag> <span>{record.instanceStatus === 20 ? "（已完成）" : ""}</span><br/>


            <span>{'ID.' + record.instanceId}</span><br/>
            <span>{record.instanceTitle}</span><br/>
            <span>{'板序号：' + record.plateNo}</span><br/>
            {
              record.batchNo && (
                <span>{'批次号：' + record.batchNo}</span>
              )
            }

          </div>
        }
      </div>
    },
  },
  {
    title: '流程过程',
    dataIndex: 'taskList',
    valueType: 'text',
    filterSearch: false,
    search: false,
    render: (text: ReactNode, record: InstanceDynamic) => {
      return <div style={{display: 'flex', height: '150px'}}>
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

const HistoryInstanceDynamic: React.FC = () => {

  const [data, setData] = useState()
  /** 国际化配置 */

  useEffect(() => {

    getUnFinishedInstanceTask().then(res => {
      setData(res.data)
    })


  }, []);


  return (
      <div
        style={{
          width: '100%',
          float: 'right',
        }}
      >
        <div className={"instanceDynamic"}>
          <Card bordered={false} style={{marginTop:"35px",background:"#fff"}}>
            <div style={{padding:"10px"}}>
              <Form>
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

                <Button
                  type="primary"
                  style={{marginLeft: '12px'}}
                  className={"no-print"}
                  onClick={async () => {
                    print()
                  }}>

                  打印
                </Button>
              </Form>
            </div>
          </Card>
          <ProTable
            headerTitle={"流程动态"}
            rowKey={() => randomUUID()}
            pagination={false}
            search={false}
            options={false}
            showHeader={true}
            scroll={{x: 1500}}
            style={{marginTop: "0px"}}
            dataSource={data}
            columns={columnsInstance}
          />
        </div>
      </div>
  );
};
export default HistoryInstanceDynamic;
