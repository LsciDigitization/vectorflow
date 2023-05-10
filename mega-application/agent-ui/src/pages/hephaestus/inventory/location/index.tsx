import {Card, FormInstance, Modal, Tag} from 'antd';
// @ts-ignore
import React, {useState, useRef, useEffect} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import WrapContent from '@/components/WrapContent';
import {Col, Menu, Row, Table} from "antd";
import {StorageLeftMenuData} from "@/pages/hephaestus/resource/storage/data";
import {MenuItemType} from "antd/lib/menu/hooks/useItems";
import {getDeviceNestData, getStorageDeviceData} from "@/pages/hephaestus/inventory/location/service";
import {PlateItem} from "@/pages/hephaestus/dynamic/instancePlate/data";
import "../location/index.css"
import {ColumnsType} from "antd/es/table";
import {LabwareData} from "@/pages/hephaestus/dynamic/instanceIteration/data";
import HoleDataTable from "@/pages/hephaestus/dynamic/instanceIteration/holeData";


const InventoryList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();

  // 左侧菜单数据 存储设备
  const [leftMenuData, setLeftMenuData] = useState<StorageLeftMenuData[]>()

  // 当前选中的设备
  const [currentMenu, setCurrentMenu] = useState('');

  // 右侧数据
  const [rightData, setRightData] = useState<any[]>([])

  // 打开板信息模态框
  const [open, setOpen] = useState(false);

  // 板数据
  const [labwareData, setLabwareData] = useState<LabwareData>()

  /** 国际化配置 */
  //

  useEffect(() => {
    getStorageDeviceData().then(res => {
      let data = res.data
      if (data) {
        let list = []
        let menus = []
        for (let i = 0; i < data.length; i++) {
          list.push({
            title: data[i].deviceName,
            label: data[i].deviceName,
            key: data[i].deviceKey
          } as StorageLeftMenuData)
        }
        menus.push({
          title: '存储设备',
          label: '存储设备',
          key: 'key',
          type: 'group',
          children: list
        })
        setLeftMenuData(menus)

        menuClick(data[0].deviceKey);
        setCurrentMenu(data[0].deviceKey)
      }
    })


  }, []);

  // 左侧菜单点击
  const menuClick = (key: string) => {
    setCurrentMenu(key)
    getDeviceNestData(key).then(res => {
      if (res.data) {
        setRightData(res.data);
      }

    })

  }

  // 板点击事件
  const handleLabwareClick = (record: any) => {
    setOpen(true)
    setLabwareData({id: record.plateId} as LabwareData)
  }

  const handleOk = () => {
    setOpen(false);
  };

  const handleCancel = () => {
    setOpen(false);
  }
  // @ts-ignore
  const columns: ColumnsType<any> = (title: string) => {
    return [
      {
        title: title,
        dataIndex: 'name',
        width: 20,
        align: "center",
        render: (_: any, record: any, index: any) => {
          let content = record.plateType ? record.plateType : ""
          content = record.nestKey
          if (record.plateId) {
            return <div style={{background: "rgba(0, 255, 0, 0.1)", padding: "10px", height: "50px", cursor: 'pointer'}}
                        onClick={() => handleLabwareClick(record)}>
              <div>{content}</div>
              <div style={{marginTop: "-5px"}}>{record.plateType}</div>
            </div>
          }
          return <div style={{background: "rgba(192, 192, 192, 0.1)", padding: "10px", height: "50px"}}>{content}</div>

        }
      },


    ];
  }


  return (
    <WrapContent>
      <div
        style={{
          width: '100%',
          float: 'right',
        }}
      >

        <Row gutter={24}  >
          <Col span={4}>
            <Card bordered={false} bodyStyle={{paddingLeft: "12px", paddingTop: "12px", paddingRight: "0px"}}>
              <Tag color="green">
                &nbsp; &nbsp; &nbsp; &nbsp;
              </Tag>
              <span>有板</span>

              <Tag color="rgba(192, 192, 192, 0.1)" style={{marginLeft: '12px'}}>
                &nbsp; &nbsp; &nbsp; &nbsp;
              </Tag>
              <span>空</span>
              <Menu
                style={{height: "800px"}}
                selectedKeys={[currentMenu]}
                mode="inline"
                items={leftMenuData as MenuItemType[]}
                onClick={({item, key, keyPath, domEvent}) => menuClick(key)}
              />
            </Card>
          </Col>
          <Col span={20} style={{paddingLeft: 0, paddingRight: 0}}>
            <Row style={{flexFlow: "nowrap", overflowX: "scroll", height: "800px"}} gutter={[24, 24]}>
              {
                rightData ? rightData.map((item) => {
                  return (
                    <Col span="2" key={item.id}  style={{marginLeft: "20px"}}>
                      <Table<any>
                        bordered={false}
                        className={"myCss"}
                        rowKey="id"
                        pagination={false}
                        showHeader={true}
                        dataSource={item.nestList}
                        // @ts-ignore
                        columns={columns(item.name + "（" + item.gridNo + "）")}
                        style={{minHeight: "800px"}}
                        onRow={(record: PlateItem) => {
                          return {
                            // 行点击事件
                          }
                        }}
                      />

                    </Col>
                  )
                }) : null
              }
              {/*</div>*/}
            </Row>
          </Col>
        </Row>
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
          labwareData={labwareData}
        />
      </Modal>
    </WrapContent>
  );
};
export default InventoryList;
