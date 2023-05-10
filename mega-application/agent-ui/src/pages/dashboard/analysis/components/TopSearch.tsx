import {Card, Col, Row, Table, Tag, Tooltip} from 'antd';
import React from 'react';
import numeral from 'numeral';
import type { DataItem } from '../data.d';

import NumberInfo from '../../../../components/NumberInfo';
import styles from '../style.less';

const columns = [

  {
    title: '资源key',
    dataIndex: 'deviceKey',
    key: 'keyword',
    render: (text: React.ReactNode) => <a href="#">{text}</a>,
  },
  {
    title: '资源类型',
    dataIndex: 'deviceTypeName',
    key: 'count',
    className: styles.alignRight,
  },
  {
    title: '资源带板数',
    dataIndex: 'resourcePlateNumber',
    key: 'index',
  },
  {
    title: '是否资源瓶颈',
    dataIndex: 'resourceBottleneck',
    key: 'index',
    render: (text: React.ReactNode) => {
      if(text){
        return   <span style={{color:"blue"}}>是</span>
      }else {
        return   <span>否</span>
      }
    },

  }
];

const TopSearch = ({
  loading,
  visitData2,
  searchData,
  dropdownGroup,
}: {
  loading: boolean;
  visitData2: DataItem[];
  dropdownGroup: React.ReactNode;
  searchData: DataItem[];
}) => {



return (
  <Card
    loading={loading}
    bordered={false}
    title="实验资源"
    // extra={dropdownGroup}
    style={{
      height: '100%',
    }}
  >
    <Row gutter={24}>
      <Col sm={12} xs={24} style={{ marginBottom: 24 }}>
        <NumberInfo
          subTitle={
            <span>
              资源总数
              {/*<Tooltip title="指标说明">*/}
              {/*  <InfoCircleOutlined style={{ marginLeft: 8 }} />*/}
              {/*</Tooltip>*/}
            </span>
          }
          gap={8}
          total={numeral(visitData2.length).format('0,0')}
          // status="up"
          // subTotal={17.1}
        />
        {/*<TinyArea xField="x" height={45} forceFit yField="y" smooth data={visitData2} />*/}
      </Col>
      {/*<Col sm={12} xs={24} style={{ marginBottom: 24 }}>*/}
      {/*  <NumberInfo*/}
      {/*    subTitle={*/}
      {/*      <span>*/}
      {/*        平均使用次数*/}
      {/*        <Tooltip title="指标说明">*/}
      {/*          <InfoCircleOutlined style={{ marginLeft: 8 }} />*/}
      {/*        </Tooltip>*/}
      {/*      </span>*/}
      {/*    }*/}
      {/*    total={30}*/}
      {/*    // status="down"*/}
      {/*    // subTotal={26.2}*/}
      {/*    // gap={8}*/}
      {/*  />*/}
      {/*  <TinyArea xField="x" height={45} forceFit yField="y" smooth data={visitData2} />*/}
      {/*</Col>*/}
    </Row>
    <Table<any>
      rowKey={(record) => record.deviceKey}
      size="small"
      columns={columns}
      dataSource={searchData}
      pagination={false}
      scroll={{x:500,y:500}}
    />
  </Card>
);
}

export default TopSearch;
