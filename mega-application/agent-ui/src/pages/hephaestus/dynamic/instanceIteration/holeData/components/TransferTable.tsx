import React, {useEffect, useState} from 'react';

import {Drawer, Form, Input, Modal} from 'antd';
import {randomUUID} from "@/utils/utils";
import ProTable, {ProColumns} from "@ant-design/pro-table";
import {TransferData, TransferTableProps} from "@/pages/hephaestus/dynamic/instanceIteration/holeData/transferData";
import {TransferPlanData} from "@/pages/hephaestus/plate/transferPlan/data";
import {getTransferPlanDataById} from "@/pages/hephaestus/plate/transferPlan/service";
import TextArea from "antd/es/input/TextArea";

import "../index.css"
const TransferTable: React.FC<TransferTableProps> = (props) => {
  const [form] = Form.useForm();


  const {dataSource} = props

  // 右侧抽屉
  const [open, setOpen] = useState(false)
  const [planData, setPlanData] = useState<TransferPlanData>()
  // 抽屉关闭
  const onClose = () => {
    setOpen(false)
  }




  const planClick = (id: string) => {

    getTransferPlanDataById(id).then(res => {

      setPlanData(res.data)
      setOpen(true)
    })
  }


  useEffect(() => {

  }, [form, props]);
  const handleOk = () => {
    form.submit();
  };
  const handleCancel = () => {
    props.onCancel();
  };

  const columns: ProColumns<TransferData>[] = [
    {
      title: '移液计划',
      dataIndex: 'planId',
      valueType: 'text',
      className:"td-min-width-large",
      width: 100,
      search: false,
      render: (_, record, index) => {
        return <a onClick={() => {
          planClick(record.planId)
        }
        }>{record.planId}</a>
      }
    },
    {
      title: '起始板',
      dataIndex: 'sourcePlateKey',
      valueType: 'text',
      className:"td-min-width-large",
      width: 100,
      filterSearch: false,
      render: (_, record, index) => {
        if (record.sourcePlate === "-1") {
          return record.sourcePlate
        }
        return record.sourcePlate + "(" + record.sourcePlateKey + ")"
      }
    },
    {
      title: '起始孔',
      dataIndex: 'sourceWellKey',
      valueType: 'text',
      className:"td-min-width-normal",
      width: 100,
      search: false
    },
    {
      title: '目标板',
      dataIndex: 'destinationPlateKey',
      valueType: 'text',
      className:"td-min-width-large",
      width: 100,
      search: false,
      render: (_, record, index) => {
        if (record.destinationPlate === "-1") {
          return record.destinationPlate
        }
        return record.destinationPlate + "(" + record.destinationPlateKey + ")"
      }
    },
    {
      title: '目标孔',
      dataIndex: 'destinationWellKey',
      valueType: 'text',
      className:"td-min-width-normal",
      width: 150,
      filterSearch: false,
      search: false
    },
    {
      title: '液体类型',
      dataIndex: 'transferType',
      valueType: 'text',
      className:"td-min-width-large",
      width: 150,
      filterSearch: false,
      search: false
    },
    {
      title: '液体',
      dataIndex: 'liquidName',
      valueType: 'text',
      className:"td-min-width-normal",
      width: 100,
      search: false
    },
    {
      title: '体积',
      dataIndex: 'volume',
      valueType: 'text',
      width: 100,
      className:"td-min-width-normal",
      filterSearch: false,
      search: false
    },
    {
      title: '枪头',
      dataIndex: 'pipetteId',
      valueType: 'text',
      className:"td-min-width-normal",
      width: 100,
      filterSearch: false,
      search: false
    },
    {
      title: '移液时间',
      dataIndex: 'transferTime',
      valueType: 'text',
      className:"td-min-width-extra-large",
      width: 100,
      filterSearch: false,
      search: false
    },


  ];

  return (
    <>
      <Modal
        width={960}
        open={props.visible}
        destroyOnClose
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <ProTable<TransferData>
          // headerTitle={title}
          // showHeader={false}
          options={false}
          search={false}
          bordered={true}
          rowKey={() => randomUUID()}
          dataSource={dataSource}
          pagination={false}
          columns={columns}
          scroll={{x:800}}
        />
      </Modal>
      <Drawer
        title="移液计划"
        closable={false}
        onClose={onClose}
        open={open}
      >
        <Form
          form={form}
          layout="vertical"
        >
          <Form.Item label="移液计划名称">
            <Input value={planData?.name} disabled/>
          </Form.Item>

          <Form.Item label="移液计划描述">
            <TextArea  value={planData?.description} disabled/>
          </Form.Item>

          <Form.Item label="起始板">
            <Input value={planData?.sourcePlateType} disabled/>
          </Form.Item>

          <Form.Item label="目标板">
            <Input value={planData?.destinationPlateType} disabled/>
          </Form.Item>

          <Form.Item label="孔范围">
            <Input value={planData?.wellRange} disabled/>
          </Form.Item>

          <Form.Item label="步骤">
            <Input value={planData?.stepKey} disabled/>
          </Form.Item>

          <Form.Item label="移液方式">
            <Input value={planData?.sampleTransferMethod} disabled/>
          </Form.Item>

          <Form.Item label="移液类型">
            <Input value={planData?.transferType} disabled/>
          </Form.Item>
          {
            planData?.liquidName ?
              (<Form.Item label="移液名称">
                <Input value={planData?.liquidName} disabled/>
              </Form.Item>) : null
          }
          <Form.Item label="移液体积">
            <Input value={planData?.volume} disabled/>
          </Form.Item>

          <Form.Item label="枪头ID">
            <Input value={planData?.pipetteId} disabled/>
          </Form.Item>

          <Form.Item label="枪头数量">
            <Input value={planData?.pipetteCount} disabled/>
          </Form.Item>
        </Form>
      </Drawer>
    </>
  );
};
export default TransferTable;
