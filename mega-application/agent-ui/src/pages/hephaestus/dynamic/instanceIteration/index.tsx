import type { FormInstance } from 'antd';
import {Modal, Tag} from 'antd';
// @ts-ignore
import React, {useState, useRef, useEffect, ReactNode} from 'react';
// @ts-ignore
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {
  getInstanceIterationData,
} from './service';
import HoleDataTable from "./holeData/index"
// @ts-ignore
import WrapContent from '@/components/WrapContent';
import {IterationData, LabwareData} from "./data.d";

const InstanceIterationTableLIst: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const [polling, setPolling] = useState<number | undefined>(5000);

  const [open, setOpen] = useState(false);

  const [labwareData,setLabwareData] = useState<LabwareData>()
  // 表格title
  const [tableTitle,setTableTitle] = useState<string>("通量动态");
  /** 国际化配置 */

  useEffect(() => {


  }, []);



  //
  const handleOk = () => {

    setTimeout(() => {
      setOpen(false);
    }, 3000);
  };

  const handleCancel = () => {
    setOpen(false);
  }

  const handleLabwareClick = (record:LabwareData) =>{
    setOpen(true)
    setLabwareData(record)
  }


  const columns: ProColumns<IterationData>[] = [
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
      title: '通量编号',
      dataIndex: 'iterationNo',
      valueType: 'text',
      search:false,
      filterSearch:false
    },
    {
      title: '耗材详情',
      dataIndex: 'labwares',
      valueType: 'text',
      search:false,
      filterSearch:false,
      render: (text: ReactNode, record: IterationData) => {
        return  <div style={{display: 'flex' }}>
          {
            record.labwares && record.labwares.map((item:LabwareData) =>(
              <div onClick={ () =>handleLabwareClick(item)} style={{cursor: 'pointer'}}>
                <Tag style={{height: '30px', width: '60px',background: item.labwareColor,padding:"5px" ,textAlign:"center"}}>
                {item.labwareName}

                </Tag>
              </div>
            ))
          }
        </div>
      },
    },
    {
      title: '开始情况',
      dataIndex: 'isConsumed',
      valueType: 'text',
      filterSearch:false,
      search:false,
      render: (text: ReactNode, record: IterationData) => {
        if(record.isConsumed === "1"){
          return   <span style={{color:"blue"}}>已开始</span>
        }else {
          return   <span>未开始</span>
        }
      },
    },
    {
      title: '开始时间',
      dataIndex: 'consumeTime',
      valueType: 'text',
      search:false
    },
    {
      title: '完成时间',
      dataIndex: 'finishTime',
      valueType: 'text',
      search:false
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
        <ProTable<IterationData>
          headerTitle={tableTitle}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          pagination={false}
          polling={polling || undefined}
          request={() =>
            getInstanceIterationData().then((res) => {

              setTableTitle("通量动态（" + res.data.length + "）")

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
        // title="孔"
        onOk={handleOk}
        onCancel={handleCancel}
        destroyOnClose
        width={1200}
      >
        <HoleDataTable
          labwareData = {labwareData}
          />
      </Modal>

    </WrapContent>
  );
};
export default InstanceIterationTableLIst;
