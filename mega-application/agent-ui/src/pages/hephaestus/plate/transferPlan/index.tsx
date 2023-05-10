import type {FormInstance} from 'antd';
// @ts-ignore
import React, {useRef, useEffect, useState, createRef} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';

// @ts-ignore
import WrapContent from '@/components/WrapContent';

import {TransferPlanData, TransferPlanParams} from "./data";
import { getTransferPlanData} from "./service";


const TransferPlanTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();

  /** 国际化配置 */
  useEffect(() => {


  }, []);


  const columns: ProColumns<TransferPlanData>[] = [

    {
      title: '计划名称',
      dataIndex: 'name',
      valueType: 'text',
      filterSearch: false
    },
    {
      title: '起始板',
      dataIndex: 'sourcePlateType',
      valueType: 'text',
      filterSearch: false,
      search: false
    },

    {
      title: '目标板',
      dataIndex: 'destinationPlateType',
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
      title: '移液范围',
      dataIndex: 'wellRange',
      valueType: 'text',
      search: false
    },
    {
      title: '步骤',
      dataIndex: 'stepKey',
      valueType: 'text',
      search: false
    },
    {
      title: '移液方式',
      dataIndex: 'sampleTransferMethod',
      valueType: 'text',
      search: false
    },
    {
      title: '移液类型',
      dataIndex: 'transferType',
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
        <ProTable<TransferPlanData>
          headerTitle={'移液计划'}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          request={(params) =>
            getTransferPlanData({...params} as TransferPlanParams).then((res) => {
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
export default TransferPlanTableList;
