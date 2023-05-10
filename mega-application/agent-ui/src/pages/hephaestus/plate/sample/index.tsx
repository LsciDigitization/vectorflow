import type {FormInstance} from 'antd';
// @ts-ignore
import React, {useRef, useEffect, useState, createRef} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';

// @ts-ignore
import WrapContent from '@/components/WrapContent';

import {SampleData} from "./data";
import {getSampleData} from "./service";


const SampleTableList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();

  /** 国际化配置 */
  useEffect(() => {


  }, []);


  const columns: ProColumns<SampleData>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '液体名称',
      dataIndex: 'name',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '液体类型',
      dataIndex: 'type',
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


  ];
  return (
    <WrapContent>
      <div
        style={{
          width: '100%',
          float: 'right',
        }}
      >
        <ProTable<SampleData>
          headerTitle={'液体列表'}
          actionRef={actionRef}
          formRef={formTableRef}
          rowKey="id"
          request={(params) =>
            getSampleData().then((res) => {
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
export default SampleTableList;
