import type {FormInstance} from 'antd';
// @ts-ignore
import React, {useRef, useEffect, useState, createRef} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';

// @ts-ignore
import WrapContent from '@/components/WrapContent';
import {Form, message} from "antd";

import {PipetteData} from "./data";
import {getPipetteData} from "./service";


const PipetteTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();

  /** 国际化配置 */
  useEffect(() => {


  }, []);


  const columns: ProColumns<PipetteData>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '枪头名称',
      dataIndex: 'name',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '描述',
      dataIndex: 'description',
      valueType: 'text',
      filterSearch: false,
    },
    {
      title: '最小容积',
      dataIndex: 'minVolume',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '最大容积',
      dataIndex: 'maxVolume',
      valueType: 'text',
      search: false,
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
        <ProTable<PipetteData>
          headerTitle={'枪头'}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          request={(params) =>
            getPipetteData().then((res) => {
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
export default PipetteTableList;
