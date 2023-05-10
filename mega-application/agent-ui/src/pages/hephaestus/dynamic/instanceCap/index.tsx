import type { FormInstance } from 'antd';
// @ts-ignore
import React, { useState, useRef, useEffect } from 'react';
// @ts-ignore
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {
  capDynamic
} from './service';
// @ts-ignore
import WrapContent from '@/components/WrapContent';
import {InstanceCap} from "./data.d";
import {InstanceDynamic} from "@/components/InstanceTaskDynamic/data";
import {instanceTaskDynamic} from "@/components/InstanceTaskDynamic/service";
import TaskDynamic from "@/components/InstanceTaskDynamic/taskDynamic";
import {Modal} from "antd";

const InstanceCapTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const [polling, setPolling] = useState<number | undefined>(5000);

  const [open, setOpen] = useState(false);
  const [currentRow, setCurrentRow] = useState<InstanceCap>();
  const [instanceTaskDynamicData, setInstanceTaskDynamic] = useState<InstanceDynamic[]>([]);

  /** 国际化配置 */

  useEffect(() => {


  }, []);

  const showModal = (record: InstanceCap) => {
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

  const columns: ProColumns<InstanceCap>[] = [
    {
      title: '刷新间隔',
      dataIndex: 'taskNo',
      valueType: 'select',
      hideInTable:true,
      valueEnum: {
        all: { text: '2秒', status: '2' },
        close: { text: '5秒', status: '5秒' },
        running: { text: '30秒', status: '30秒' },
        online: { text: '5分钟', status: '5分钟' }
      },
    },
    {
      title: '实例',
      dataIndex: 'instanceId',
      valueType: 'text',
      search:false,
      filterSearch:false
    },
    {
      title: 'deviceKey',
      dataIndex: 'deviceKey',
      valueType: 'text',
      filterSearch:false,
      search:false
    },
    {
      title: '设备类型',
      dataIndex: 'deviceType',
      valueType: 'text',
      search:false
    },

    {
      title: '开盖时间',
      dataIndex: 'openCapTime',
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

        <ProTable<InstanceCap>
          headerTitle={'开盖机动态'}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          pagination={false}
          polling={polling || undefined}
          onRow={(record: InstanceCap) => {
            return {
              // 行点击事件
              onClick: () => {
                showModal(record);
                setCurrentRow(record);
              }
            }
          }}
          request={() =>
            capDynamic().then((res) => {
              return {
                data: res.data,
                total: res.data.length,
                success: true,
              };
              setPolling(5000);


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
export default InstanceCapTableList;
