import type {FormInstance} from 'antd';
// @ts-ignore
import React, {useRef, useEffect, useState, createRef} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';

// @ts-ignore
import WrapContent from '@/components/WrapContent';
import {Form, message} from "antd";

import {TransferData, TransferParams} from "./data";
import {getTransferData} from "./service";


const InstancePlateTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();

  /** 国际化配置 */
  useEffect(() => {


  }, []);


  const columns: ProColumns<TransferData>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '移液计划',
      dataIndex: 'plateId',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '起始板',
      dataIndex: 'sourcePlateKey',
      valueType: 'text',
      filterSearch: false,
      render: (_, record, index) => {
        if(record.sourcePlate === "-1"){
          return record.sourcePlate
        }
        return record.sourcePlate + "（" + record.sourcePlateKey + "）"
      }
    },
    {
      title: '起始孔',
      dataIndex: 'sourceWellKey',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '目标板',
      dataIndex: 'destinationPlateKey',
      valueType: 'text',
      search: false,
      render: (_, record, index) => {
        if(record.destinationPlate ==="-1"){
          return record.destinationPlate
        }
        return record.destinationPlate + "（" + record.destinationPlateKey + "）"
      }
    },
    {
      title: '目标孔',
      dataIndex: 'destinationWellKey',
      valueType: 'text',
      search: false,
    },
    {
      title: '液体',
      dataIndex: 'liquidName',
      valueType: 'text',
      search: false
    },
    {
      title: '移液体积',
      dataIndex: 'volume',
      valueType: 'text',
      search: false
    },
    {
      title: '移液时间',
      dataIndex: 'transferTime',
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
        <ProTable<TransferData>
          headerTitle={'移液记录'}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          request={(params) =>
            getTransferData({...params} as TransferParams).then((res) => {
              return {
                data: res.data.items,
                total: res.data.meta.total,
                current: res.data.meta.currentPage,
                success: true,
              };
            })
          }
          columns={columns}
        />
      </div>
    </WrapContent>
  );
};
export default InstancePlateTableList;
