import type {FormInstance} from 'antd';
// @ts-ignore
import React, {useState, useRef, useEffect} from 'react';
// @ts-ignore
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import WrapContent from '@/components/WrapContent';
import {Button, Card, Col, Menu, Row, Table} from "antd";
import {StorageCommandData, StorageData, StorageLeftMenuData} from "@/pages/hephaestus/resource/storage/data";
import {MenuItemType} from "antd/lib/menu/hooks/useItems";

const StorageList: React.FC = () => {
  const formTableRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();


  const [leftMenuData, setLeftMenuData] = useState<StorageLeftMenuData[]>()

  const [storageCommandData, setStorageCommandData] = useState<StorageCommandData[]>()

  const [storageData, setStorageData] = useState<StorageData[]>()

  const getLeftMenuDataFunc = () => {
    let list = []
    for (let i = 0; i < 10; i++) {
      list.push({
        icon: "setting",
        title: "title" + i,
        label: "label" + i,
        key: "key" + i
      })
    }
    return list
  }

  const getStorageCommandDataFunc = () => {
    let list = []
    for (let i = 0; i < 10; i++) {
      list.push({
        key: "icon" + i,
        name: "name" + i,
        disable: false
      })
    }
    return list
  }
  /** 国际化配置 */
    //
  const getStorageDataFunc = () => {
      let list = []
      for (let i = 0; i < 10; i++) {
        list.push({
          id: "id" + i,
          // 板位
          nest: "nest" + i,
          // 耗材
          plate: "plate" + i,
          // 条码
          barcode: "barcode" + i,
          // 状态
          status: "running",
          // 通量
          flux: i,
          // 过期
          expired: "true",
          // 取消
          cancel: "false",
        })
      }
      return list
    }

  useEffect(() => {
    setLeftMenuData(getLeftMenuDataFunc())
    setStorageCommandData(getStorageCommandDataFunc())
    setStorageData(getStorageDataFunc())
  }, []);
  const [collapsed, setCollapsed] = useState(false);

  const toggleCollapsed = () => {
    setCollapsed(!collapsed);
  };



  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: '板位',
      dataIndex: 'nest',
      key: 'nest',
    },
    {
      title: '耗材',
      dataIndex: 'plate',
      key: 'plate',
    },
    {
      title: '条码',
      dataIndex: 'barcode',
      key: 'barcode',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
    },
    {
      title: '通量',
      dataIndex: 'flux',
      key: 'flux',
    },
    {
      title: '过期',
      dataIndex: 'expired',
      key: 'expired',
    },
    {
      title: '取消',
      dataIndex: 'cancel',
      key: 'cancel',


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

        <Row gutter={24}>
          <Col span={4}>

            {/*<Button type="primary" onClick={toggleCollapsed} style={{ marginBottom: 16 }}>*/}
            {/*  {collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}*/}
            {/*</Button>*/}
            <Menu
              defaultSelectedKeys={['1']}
              mode="inline"
              inlineCollapsed={collapsed}
              items={leftMenuData as MenuItemType[]}
            />
          </Col>
          <Col span={20}>
            <Card style={{width: "100%"}}>
              {
                storageCommandData ? storageCommandData.map(item => {
                  return <Button type="primary" style={{marginLeft:"12px"}}>{item.name}</Button>
                }) : <div/>

              }
            </Card>

            <div style={{marginTop: "20px"}}>

              <Table
                dataSource={storageData}
                columns={columns}
              />

            </div>
          </Col>
        </Row>


      </div>


    </WrapContent>
  );
};
export default StorageList;
