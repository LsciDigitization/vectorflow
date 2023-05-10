import type {FormInstance} from 'antd';
import {Modal, Tag} from 'antd';
// @ts-ignore
import React, {useState, useRef, useEffect} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {
  deviceTaskDynamic
} from './service';
// @ts-ignore
import WrapContent from '@/components/WrapContent';
import {DeviceTask} from "./data.d";
import {instanceTaskDynamic} from "@/components/InstanceTaskDynamic/service";
import {InstanceDynamic} from "@/components/InstanceTaskDynamic/data";
import TaskDynamic from "@/components/InstanceTaskDynamic/taskDynamic";

const DeviceTaskTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const [polling, setPolling] = useState<number>(5000);
  const [currentRow, setCurrentRow] = useState<DeviceTask>();
  const [open, setOpen] = useState(false);
  const [instanceTaskDynamicData, setInstanceTaskDynamic] = useState<InstanceDynamic[]>([]);

  // 表格title
  const [tableTitle,setTableTitle] = useState<string>("设备动态");
  /** 国际化配置 */

  useEffect(() => {


  }, []);

  const showModal = (record: DeviceTask) => {
    setOpen(true);

    if (currentRow) {
      instanceTaskDynamic(record.instanceId).then(res => {
        if (res.data) {
          setInstanceTaskDynamic([res.data])
        }
      })
    } else {
      setInstanceTaskDynamic([])
    }
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

  const selectData = [
    {value: '2', label: '2'},
    {value: '5', label: '5秒'},
    {value: '30', label: '30秒'},
    {value: '300', label: '5分钟'}
  ]
  // @ts-ignore
  const columns: ProColumns<DeviceTask>[] = [
    {
      title: '刷新间隔',
      dataIndex: 'timeInterval',
      valueType: 'select',
      hideInTable: true,
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
      // renderFormItem: (_, {
      //   onSelect
      //   ,defaultRender }) => {
      //   console.log(onSelect)
      //   return defaultRender(_);
      // },
      // renderFormItem: (_, {onSelect, type, defaultRender, ...rest }, form) =>{
      //
      // }

      // renderFormItem:(config) =>{
      //   onchange()
      // }
      // c: (_, record)=>{
      //   console.log(record)
      // }
    },
    {
      title: '设备类型',
      dataIndex: 'deviceType',
      valueType: 'text',
      search: false,
      filterSearch: false
    },
    {
      title: 'deviceKey',
      dataIndex: 'deviceKey',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '任务名称',
      dataIndex: 'taskName',
      valueType: 'text',
      search: false
    },

    {
      title: '任务状态',
      dataIndex: 'taskStatus',
      valueType: 'text',
      search: false,
      render: (_, record) => {
        if (record.taskStatus === 'await') {
          return <Tag color=''> {record.taskStatus}</Tag>
        }
        return <Tag color='#87d068'>{record.taskStatus}</Tag>

      },
    },
    {
      title: '任务命令',
      dataIndex: 'taskCommand',
      valueType: 'text',
      search: false
    },
    {
      title: '任务运行超时时间',
      dataIndex: 'taskTimeoutSecond',
      valueType: 'text',
      search: false
    },
  ];
  return (
    <WrapContent>
      <div
        style={{
          width: '100%',
          float: 'right',
        }}
      >
        <ProTable<DeviceTask>
          headerTitle={tableTitle}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          pagination={false}
          polling={5000}
          onRow={(record: DeviceTask) => {
            return {
              // 行点击事件
              onClick: () => {
                showModal(record);
                setCurrentRow(record);
              }
            }
          }}
          request={() =>
            deviceTaskDynamic().then((res) => {
              setTableTitle("设备动态（" + res.data.length + "）")

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
          InstanceDynamics={instanceTaskDynamicData}
        />
      </Modal>

    </WrapContent>
  );
};
export default DeviceTaskTableList;
