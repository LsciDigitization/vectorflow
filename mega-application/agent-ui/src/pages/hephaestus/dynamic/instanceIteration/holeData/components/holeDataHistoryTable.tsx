import React, {useEffect, useState} from 'react';

import { Form, Modal} from 'antd';
import {HoleDataHistoryTableProps, HoleHistoryData} from "./../data";
import {randomUUID} from "@/utils/utils";
import ProTable, {ProColumns} from "@ant-design/pro-table";
import {getHoleDataHistoryData} from "./../service";

const HoleDataHistoryTable: React.FC<HoleDataHistoryTableProps> = (props) => {
  const [form] = Form.useForm();
  console.log(props)


  const {holeDataId,title} = props

  const [dataSource,setDataSource] = useState<HoleHistoryData[]>()

  useEffect(() => {
    if(holeDataId){
      getHoleDataHistoryData(holeDataId).then(res =>{
        setDataSource(res.data)
      })
    }


  }, [form, props]);
  const handleOk = () => {
    form.submit();
  };
  const handleCancel = () => {
    props.onCancel();
   };

  const columns: ProColumns<HoleHistoryData>[] = [
    {
      title: '版本',
      dataIndex: 'version',
      valueType: 'text',
      search:false
    },
    {
      title: '操作时间',
      dataIndex: 'createTime',
      valueType: 'text',
      search:false
    },
    {
      title: '板类型',
      dataIndex: 'plateType',
      valueType: 'text',
      filterSearch:false
    },
    {
      title: '板ID',
      dataIndex: 'instancePlateId',
      valueType: 'text',
      filterSearch:false,
      search:false
    },

    {
      title: '数据',
      dataIndex: 'data',
      valueType: 'text',
      filterSearch:false,
      search:false
    },
    {
      title: '数据类型',
      dataIndex: 'dataType',
      valueType: 'text',
      filterSearch:false,
      search:false
    },
    {
      title: '容量',
      dataIndex: 'capacity',
      valueType: 'text',
      filterSearch:false,
      search:false
    },
    {
      title: '浓度',
      dataIndex: 'capacity',
      valueType: 'text',
      filterSearch:false,
      search:false
    },
    {
      title: '孔key',
      dataIndex: 'holeKey',
      valueType: 'text',
      filterSearch:false,
      search:false
    },



  ];

  return (
    <Modal
      width={980}
      title={title}
      visible={props.visible}
      destroyOnClose
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <ProTable<HoleHistoryData>
        // headerTitle={title}
        // showHeader={false}
        options={false}
        search={false}
        bordered={true}
        rowKey={() => randomUUID()}
        dataSource={dataSource}
        pagination={false}
        // scroll={{x: 1500}}
        columns={columns}
      />
    </Modal>
  );
};
export default HoleDataHistoryTable;
