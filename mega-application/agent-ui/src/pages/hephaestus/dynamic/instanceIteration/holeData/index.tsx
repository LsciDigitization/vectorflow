import {Col, Form, FormInstance, Input, List, Row} from 'antd';
// @ts-ignore
import React, {useRef, useEffect, useState} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';

// @ts-ignore


import {InstancePlateData} from "./data";
import {getHoleDataByPlateId, getSampleData} from "./service";
import {randomUUID} from "@/utils/utils";
import TransferTable from "./components/TransferTable"

import {SampleData} from "@/pages/hephaestus/plate/sample/data";
import {getTransferData} from "@/pages/hephaestus/dynamic/instanceIteration/holeData/transferService";
import {TransferData} from "@/pages/hephaestus/dynamic/instanceIteration/holeData/transferData";


const HoleDataTableList = (props: any) => {
  let {labwareData} = props
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();

  const [transferModalVisible, setTransferModalVisible] = useState<boolean>(false);


  const [sampleData, setSampleData] = useState<SampleData>();
  const handleTableOnCancel = () => {
    setTransferModalVisible(false);
  };
  const handleTableOnSubmit = () => {
    setTransferModalVisible(false);
  };
  const [form] = Form.useForm();
  const plateId = labwareData.id
  // const { id } = history.
  // this.props.match.params;

  const [columns, setColumns] = useState<ProColumns<any>[]>([])

  const [tableHeadTitle, setTableHeadTitle] = useState<string>("")

  const [liquidId,setLiquidId] = useState<string>("")

  const [dataSource, setDataSource] = useState<any>()

  // 孔id
  const [wellId,setWellId] = useState("-1");
  // 移液详情
  const [transferList,setTransferList] = useState<TransferData[]>([]);
  // 当前点击的孔id 用来做样式切换
  let currentWellId = "-1";

  const divStyle = {
    background: "burlywood",
    color: "#000",
    width: "30px",
    height: "30px",
    borderRadius: " 50%",
    // font: "3px",
    justifyContent: "center",
    alignItems: "center",
    textAlign: "center",
    display: "flex",
    cursor: 'pointer'
  }
  const clickStyle = {
    background: "#00800052",
    color: "#000",
    width: "30px",
    height: "30px",
    borderRadius: " 50%",
    // font: "3px",
    justifyContent: "center",
    alignItems: "center",
    textAlign: "center",
    display: "flex",
    cursor: 'pointer'
  }

  /** 国际化配置 */

  useEffect(() => {
    let cols: ProColumns<any>[] = []
    // @ts-ignore
    getHoleDataByPlateId(plateId).then(res => {
      if (res) {
        let data = res.data;
        if (data) {
          let iterationTimes = data.holeCols * data.holeRows
          setTableHeadTitle(data.plateName + "（" + data.plateKey + iterationTimes + "孔" + " " + data.holeCols + "列 X " + data.holeRows + "行）");
          data.keys?.map((item: string) => {

            cols.push({
              dataIndex: item,
              valueType: 'text',
              filterSearch: false,
              search: false,
              onCell: (record, rowIndex) => ({

                onClick: () => {
                  if (record[item]) {
                    setWellId(record[item].id)
                    onWellClick(record[item].id,record[item].liquidId,record[item].sampleId);
                  }
                }
              }),
              render: (_, record, index) => {
                // 列的数据
                let colData = record[item];
                if (colData) {
                  let wellKey = colData.wellKey;
                  let styleSelected = currentWellId == colData.id

                  if (colData.version > 0) {
                    // @ts-ignore
                    return <div style={currentWellId == colData.id?clickStyle:divStyle}>{wellKey}</div>
                  } else {
                    // @ts-ignore
                    return <div style={currentWellId == colData.id?clickStyle:divStyle}>{wellKey}</div>
                  }
                } else {
                  return "第" + (index + 1) + "行"
                }
              }
            })
          })
          setColumns(cols);
          setDataSource(data.dataSources)
          // 默认第一个孔点击
          let defaultWellId =  data.dataSources[0]["col-0"].id
          let defaultLiquidId =  data.dataSources[0]["col-0"].liquidId
          let defaultSampleId =  data.dataSources[0]["col-0"].sampleId

          onWellClick(defaultWellId,defaultLiquidId,defaultSampleId);

        }
      }

    })

  }, []);

  const onWellClick = (wellId: string,liquidId: string,sampleId: string) =>{
    setWellId(wellId)
    currentWellId  =wellId
    // 液体id
    setLiquidId(liquidId);

    // 拿到id 去查询
    getTransfer(wellId);
    if(sampleId){
      getSample(sampleId);
    }


  }

// 获取样本数据
  const getSample = (id: string) => {
    getSampleData(id).then(res => {
      setSampleData(res.data)
    })
  }


  // 样本id 点击事件
  const sampleClick = () =>{
    // 打开模态框
    setTransferModalVisible(true)
  }

  // 获取移液记录
  const getTransfer = (wellId:string) =>{
    getTransferData(wellId).then(res =>{
      if(res.data){
        setTransferList(res.data)
      }

    })
  }

  return (
    <>
      <div
        style={{
          width: '100%',
          float: 'right',
          height:'650px',
          marginTop:"24px"
        }}
      >
        <Row>
          <Col span={16}>
            <ProTable<InstancePlateData>
              headerTitle={tableHeadTitle}
              showHeader={false}
              search={false}
              bordered={true}
              actionRef={actionRef}
              formRef={formTableRef}
              rowKey={() => randomUUID()}
              dataSource={dataSource}
              pagination={false}
              columns={columns}
            />
          </Col>
          {/*左边表格的间距是这个样式 保持对齐*/}

          <Col span={8}   style={{height:"650px", overflow: 'auto',paddingTop:"16px"}}>
            <Form
              form={form}
              layout="vertical"
            >
              <Form.Item>
                {/*左边表格的title是这个样式 保持对齐*/}
                <span className="ant-form-text" style={{fontSize:"16px",fontWeight:"500"}}>一号车间-2号冰箱-4℃冷藏-第二层-3号盒子-A2</span>
              </Form.Item>

              {
                // 样板没有liquid

                // 标板没有sampleId

                // 空板都没有

              }
              <Form.Item label="板ID">
                <a onClick={() => sampleClick()}> {wellId}</a>
              </Form.Item>

              <Form.Item label="样品ID">
                <a onClick={() => sampleClick()}> {sampleData?.id}</a>
              </Form.Item>

              <Form.Item label="样品名称">
                <Input value={sampleData?.name} disabled/>
              </Form.Item>

              <Form.Item
                label="样品类型"
              >
                <Input value={sampleData?.type} disabled/>
              </Form.Item>

              <Form.Item
                label="样品溶度"
              >
                <Input value={sampleData?.concentration} disabled/>
              </Form.Item>
              <Form.Item
                label="样品体积"
              >
                <Input value={sampleData?.wellTotalVolume} disabled/>
              </Form.Item>

              <List
                itemLayout="horizontal"
                dataSource={transferList}
                renderItem={(item) => (
                  <List.Item>
                    <List.Item.Meta
                      // avatar={<Avatar src={`https://joesch.moe/api/v1/random?key=${index}`} />}
                      // title={<a href="https://ant.design">转上清</a>}
                      description={item?.transferDescription}
                    />
                  </List.Item>
                )}
              />
            </Form>
          </Col>
        </Row>

      </div>

      {/*<TransferList*/}
      {/*  onSubmit={handleTableOnSubmit}*/}
      {/*  onCancel={handleTableOnCancel}*/}
      {/*  visible={transferModalVisible}*/}
      {/*  title={tableHeadTitle}*/}
      {/*  wellId={wellId}*/}
      {/*/>*/}

      <TransferTable
        title ={""}
        onSubmit={handleTableOnSubmit}
        onCancel={handleTableOnCancel}
        visible={transferModalVisible}
        dataSource={transferList}
      />
    </>
  );
};
export default HoleDataTableList;
