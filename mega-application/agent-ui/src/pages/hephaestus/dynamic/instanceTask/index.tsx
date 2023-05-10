import type { FormInstance } from 'antd';
import {Modal, Tag} from 'antd';
// @ts-ignore
import React, { useState, useRef, useEffect } from 'react';
// @ts-ignore
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {
  instanceTaskList
} from './service';
// @ts-ignore
import WrapContent from '@/components/WrapContent';
import {instanceTaskDynamic} from '@/components/InstanceTaskDynamic/service'
import {InstanceTaskType} from "./data.d";
import {InstanceDynamic} from "@/components/InstanceTaskDynamic/data";
import TaskDynamic from "@/components/InstanceTaskDynamic/taskDynamic";

const InstanceTaskTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const [polling, setPolling] = useState<number | undefined>(5000);

  const [open, setOpen] = useState(false);
  const [currentRow, setCurrentRow] = useState<InstanceTaskType>();
  const [instanceTaskDynamicData, setInstanceTaskDynamic] = useState<InstanceDynamic[]>([]);

  // 表格title
  const [tableTitle,setTableTitle] = useState<string>("任务动态");
  /** 国际化配置 */

  useEffect(() => {


  }, []);


  const showModal = (record: InstanceTaskType) => {
    setOpen(true);
    console.log(currentRow)
    // todo 写死原因 前端丢失精度问题
    // 解决方案1 、替换前端请求框架为 Axios
    // 解决方案2、后端对返回的Long类型统一处理为String
    // umi-request 暂无处理方法
    instanceTaskDynamic(record.instanceId).then(res => {
      if(res.data){
        setInstanceTaskDynamic([res.data])
      }

    })
  };
  //
  const handleOk = () => {

    setTimeout(() => {
      setOpen(false);
    }, 3000);
  };

  const handleCancel = () => {
    setOpen(false);
  }
  const columns: ProColumns<InstanceTaskType>[] = [
    {
      title: '刷新间隔',
      dataIndex: 'timeInterval',
      valueType: 'select',
      hideInTable:true,
      initialValue: '5秒',
      valueEnum: {
        2: {text: '2秒'},
        5: {text: '5秒'},
        30: {text: '30秒'},
        300: {text: '5分钟'},
      },
      search: {
        transform: (value: any) => {
          setPolling(value * 1000)
          actionRef.current?.reload();
          return {polling : value * 1000};
        },
      },
    },
    {
      title: '任务编号',
      dataIndex: 'taskNo',
      valueType: 'text',
      search:false,
      filterSearch:false
    },
    {
      title: '板序号',
      dataIndex: 'plateNo',
      valueType: 'text',
      filterSearch:false,
      search:false
    },
    {
      title: '任务名称',
      dataIndex: 'taskName',
      valueType: 'text',
      search:false
    },
    {
      title: 'deviceKey',
      dataIndex: 'deviceKey',
      valueType: 'text',
      search:false
    },
    {
      title: '设备类型',
      dataIndex: 'deviceType',
      valueType: 'text',
      search:false
    },
    {
      title: '任务状态',
      dataIndex: 'taskStatus',
      valueType: 'text',
      search:false,
      render: (_, record) => {
          if(record.taskStatus ==='await'){
            return <Tag color=''> {record.taskStatus}</Tag>
          }
          return <Tag color='#87d068'>{record.taskStatus}</Tag>

      },

      // valueEnum: operatorTypeOptions
    },
    {
      title: '板池类型',
      dataIndex: 'experimentPoolType',
      valueType: 'text',
      search:false
    },
    {
      title: '任务命令',
      dataIndex: 'taskCommand',
      valueType: 'text',
      search:false
    },
    {
      title: '将要执行时间(秒)',
      dataIndex: 'timeoutSecond',
      valueType: 'text',
      search:false
    },
    {
      title: '任务阶段',
      dataIndex: 'stepKey',
      valueType: 'text',
      search:false
    }
  ];
  return (
    <WrapContent>
      <div
        style={{
          width: '100%',
          float: 'right',
        }}
      >
        <ProTable<InstanceTaskType>
          headerTitle={tableTitle}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          pagination={false}
          polling={ 5000}
          onRow={(record: InstanceTaskType) => {
            return {
              // 行点击事件
              onClick: () => {
                showModal(record);
                setCurrentRow(record);
              }
            }
          }}
          request={() =>
            instanceTaskList().then((res) => {

              setTableTitle("任务动态（" + res.data.length + "）")

              return {
                data: res.data,
                total: res.data.length,
                success: true,
              };
            })
          }
          columns={columns}
        />
      </div>
      <Modal
        open={open}
        title="执行过程"
        onOk={handleOk}
        onCancel={handleCancel}
        width="1500"
      >

        <TaskDynamic
          InstanceDynamics= {instanceTaskDynamicData}
        />
      </Modal>

    </WrapContent>
  );
};
export default InstanceTaskTableList;
