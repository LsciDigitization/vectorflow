import type {ReactNode} from 'react';
import React, {useState, useEffect} from 'react';
import type {ProColumns} from '@ant-design/pro-table';
import {
  instancePlateDynamic
} from './service';
import {instanceTaskDynamic} from '@/components/InstanceTaskDynamic/service'
import WrapContent from '@/components/WrapContent';
import type {PlateItem} from "./data.d";
import {Form, SelectProps} from "antd";
import {Card, Col, Row, Select, Tag, Modal} from "antd";
import ProTable from "@ant-design/pro-table";
import TaskDynamic from "@/components/InstanceTaskDynamic/taskDynamic";
import type {InstanceDynamic} from "@/components/InstanceTaskDynamic/data";
// @ts-ignore
import {NodeJS} from "timers";
import {randomUUID} from "@/utils/utils";


const options: SelectProps['options'] = [];
options.push({value: 2, label: '2秒'})
options.push({value: 5, label: '5秒'})
options.push({value: 30, label: '30秒'})
options.push({value: 300, label: '5分钟'})
let timer: NodeJS.Timer = null;
const InstancePlateTableList: React.FC = () => {
  const [plateDynamic, setPlateDynamicMap] = useState<{} | undefined>({});

  const [open, setOpen] = useState(false);
  const [currentRow, setCurrentRow] = useState<PlateItem>();
  const [instanceTaskDynamicData, setInstanceTaskDynamic] = useState<InstanceDynamic[]>([]);

  const [polling, setPolling] = useState<number | undefined>(5000);

  const getInstancePlateDynamic = () => {
    instancePlateDynamic().then(res => {
      console.log(new Date())
      setPlateDynamicMap(res.data)
    })
  }


  /** 国际化配置 */
  useEffect(() => {

    getInstancePlateDynamic()
    timer = setInterval(getInstancePlateDynamic, 5000)
  }, []);

  const showModal = (record: PlateItem) => {

    // todo 写死原因 前端丢失精度问题
    // 解决方案1 、替换前端请求框架为 Axios
    // 解决方案2、后端对返回的Long类型统一处理为String
    // umi-request 暂无处理方法
    if (record.instanceId) {
      setOpen(true);
      instanceTaskDynamic(record.instanceId).then(res => {
        if (res.data) {
          setInstanceTaskDynamic([res.data])
        }

      })
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
  const handleRefreshIntervalChange = () => {

  }
  const columns: ProColumns<PlateItem>[] = [

    {
      title: '板序号',
      dataIndex: 'plateNo',
      valueType: 'text',
      filterSearch: false,
      search: false
    },
    {
      title: '任务状态',
      dataIndex: 'transientStatus',
      valueType: 'text',
      search: false,
      render: (text: ReactNode, record: PlateItem) => {
        if (record.transientStatus === 3) {
          return <Tag color='blue'> 已完成</Tag>
        }
        if (record.transientStatus === 2) {
          return <Tag color='green'> 已消费</Tag>
        }
        if (record.transientStatus === 1) {
          return <Tag color=''>未消费</Tag>
        }
        return "";
      },
    },
    {
      title: '消费/完成时间',
      dataIndex: 'transientTime',
      align: 'left',
      search: false,
    }
  ];

  // @ts-ignore
  // @ts-ignore
  return (
    <WrapContent>
      <div
        style={{
          width: '100%',
          float: 'right',
        }}
      >
        <div className={"ant-pro-table"}>
          <div className="ant-pro-card ant-pro-table-search ant-pro-table-search-query-filter">
            <form
              autoComplete="off"
              className="ant-form ant-form-horizontal ant-pro-form-query-filter"
            >
              <input type="text" style={{display: 'none'}}/>
              <div className="ant-row ant-row-start" style={{marginLeft: '-12px', marginRight: '-12px'}}>
                <div className="ant-col ant-col-6" style={{paddingLeft: '12px', paddingRight: '12px'}}>
                  <div className="ant-form-item" style={{flexWrap: 'nowrap'}}>
                    <div className="ant-row ant-form-item-row">
                      <div className="ant-col ant-form-item-label" style={{flex: '0 0 80px'}}>
                        <label htmlFor="timeInterval" className="" title="刷新间隔">
                          刷新间隔
                        </label>
                      </div>
                      <div className="ant-col ant-form-item-control" style={{maxWidth: 'calc(100% - 70px)'}}>
                        <div className="ant-form-item-control-input">
                          <div className="ant-form-item-control-input-content">
                            {/*<Select*/}
                            {/*  id="timeInterval"*/}
                            {/*  defaultValue={"5秒"}*/}

                            {/*  onChange={handleRefreshIntervalChange}*/}
                            {/*  className="ant-select ant-select-in-form-item ant-pro-filed-search-select ant-select-single ant-select-allow-clear ant-select-show-arrow"*/}
                            {/*  style={{ width: '100%' }}*/}
                            {/*>*/}
                            {/*  <Option value="2">2秒</Option>*/}
                            {/*  <Option value="5">5秒</Option>*/}
                            {/*  <Option value="30">30秒</Option>*/}
                            {/*  <Option value="5">300</Option>*/}
                            {/*</Select>*/}
                            <Select
                              id="timeInterval"
                              defaultValue={5}
                              onChange={handleRefreshIntervalChange}
                              className="ant-select ant-select-in-form-item ant-pro-filed-search-select ant-select-single ant-select-allow-clear ant-select-show-arrow"
                              style={{width: '100%'}}
                              options={options}
                              onSelect={(value) => {
                                clearInterval(timer)
                                setPolling(value * 1000)
                                timer = setInterval(getInstancePlateDynamic, value * 1000)
                              }}
                            >

                            </Select>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="ant-col ant-col-6 ant-col-offset-12"
                     style={{paddingLeft: '12px', paddingRight: '12px', textAlign: 'right'}}>
                  <div className="ant-form-item pro-form-query-filter-actions">
                    <div className="ant-row ant-form-item-row">
                      <div className="ant-col ant-form-item-label" style={{flex: '0 0 80px'}}></div>
                      <div className="ant-col ant-form-item-control" style={{maxWidth: 'calc(100% - 80px)'}}>
                        {/*<button type="submit" className="ant-btn ant-btn-primary">*/}
                        {/*  查询*/}
                        {/*</button>*/}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>

        <Row style={{marginTop: "12px", flexFlow: "nowrap", overflowX: "scroll"}} gutter={[24, 24]}>
          {

            plateDynamic ? plateDynamic && Object.keys((plateDynamic)).map((item: string, index: number) => {
              if (item) {
                const data = plateDynamic[item]
                let title = data[0].transientExperimentPoolTypeName
                if (data) {

                  let consumedCount = 0;
                  let unfininshCount = 0;
                  for (let i = 0; i < data.length; i++) {
                    if (data[i].transientStatus === 1) {
                      consumedCount++;
                    }
                    if (data[i].transientStatus < 3) {
                      unfininshCount++;
                    }
                  }
                  title = data[0].transientExperimentPoolTypeName + "（" + consumedCount + "/" + unfininshCount + "）"
                }
                return (
                  <Col span="8" key={randomUUID()}>
                    <Card>
                      <ProTable<PlateItem>
                        headerTitle={title}
                        rowKey="id"
                        key={index}
                        pagination={false}
                        search={false}
                        options={false}
                        showHeader={true}
                        dataSource={data}
                        columns={columns}
                        onRow={(record: PlateItem) => {
                          return {
                            // 行点击事件
                            onClick: () => {
                              showModal(record);
                              setCurrentRow(record);
                            }
                          }
                        }}
                      />
                    </Card>
                  </Col>
                )
              } else {
                return ''
              }
            }) : (
              <Col span="24">
                <Card>
                  <ProTable<PlateItem>
                    rowKey="id"
                    pagination={false}
                    search={false}
                    options={false}
                    showHeader={true}
                    dataSource={[]}
                    columns={columns}
                    onRow={(record: PlateItem) => {
                      return {
                        // 行点击事件
                        onClick: () => {

                        }
                      }
                    }}
                  />
                </Card>
              </Col>
            )
          }

        </Row>
      </div>
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

export default InstancePlateTableList;
