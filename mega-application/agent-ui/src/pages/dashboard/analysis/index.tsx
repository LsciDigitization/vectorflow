import type {FC} from 'react';
import {Suspense, useEffect, useState} from 'react';
import {EllipsisOutlined} from '@ant-design/icons';
import {Card, Col, Descriptions, Dropdown, Menu, Row} from 'antd';
import {GridContent} from '@ant-design/pro-layout';
import type {RadioChangeEvent} from 'antd/es/radio';

import IntroduceRow from './components/IntroduceRow';
import TopSearch from './components/TopSearch';
import ProportionSales from './components/ProportionSales';

import {fakeChartData, getDashboard} from './service';

import type {AnalysisData} from './data.d';
import styles from './style.less';
import WrapContent from '@/components/WrapContent';
import {
  getCountGroupByPlateNo,
  getResourceColorByRunning
} from "@/pages/hephaestus/experiment/group/experimentHistory/gantt/service";
import {plateCount} from "@/pages/hephaestus/dynamic/instancePlate/service";
import {instanceTaskCount} from "@/pages/hephaestus/dynamic/instanceTask/service";
import {DashboardData, InstancePlateNoCountData, InstancePlateNoSummaryData, InstanceTaskCountData} from "./data.d";


type AnalysisProps = {
  dashboardAndanalysis: AnalysisData;
  loading: boolean;
};

type SalesType = 'all' | 'online' | 'stores';

const Analysis: FC<AnalysisProps> = () => {
  const [salesType, setSalesType] = useState<SalesType>('all');
  // const [currentTabKey, setCurrentTabKey] = useState<string>('');
  // const [rangePickerValue, setRangePickerValue] = useState<RangePickerValue>(
  //   getTimeDistance('year'),
  // );

  // const {loading, data} = useRequest(fakeChartData);
  const loading = false

  const [resourceList, setResourceList] = useState<any>()

  const [introduceRowData, setIntroduceRowData] = useState<any>()


  const [dashboardData, setDashboardData] = useState<DashboardData>()
  // 通量详情
  const [plateNoData, setPlateNoData] = useState<InstancePlateNoCountData[]>()

  // 板子汇总统计
  const [plateNoSummaryData, setPlateNoSummaryData] = useState<InstancePlateNoSummaryData>()

  // 任务统计
  const [instanceTaskCountData, setInstanceTaskCountData] = useState<InstanceTaskCountData>()

  const getAllData = async () => {


    // 获取资源数据
    getResourceColorByRunning().then(res => {
      if (res.data) {
        setResourceList(res.data);
      }
    })

    let data = {}
    // 获取实例任务统计数据
    instanceTaskCount().then(res => {
      setInstanceTaskCountData(res.data)
      setIntroduceRowData(data)
    })

    // 获取板池统计数据
    plateCount().then(res => {
      setPlateNoSummaryData(res.data)
      data['plateData'] = res.data
      setIntroduceRowData(data)
    })

    getDashboard().then(res => {
      setDashboardData(res.data)
    })

    setIntroduceRowData(data)

    getCountGroupByPlateNo().then(res => {
      console.log(res)
      setPlateNoData(res.data)
    })

  }

  useEffect(() => {

    getAllData()

  }, []);


  let salesPieData;
  // if (salesType === 'all') {
  //   salesPieData = data?.salesTypeData;
  // } else {
  //   salesPieData = salesType === 'online' ? data?.salesTypeDataOnline : data?.salesTypeDataOffline;
  // }

  const menu = (
    <Menu>
      <Menu.Item>操作一</Menu.Item>
      <Menu.Item>操作二</Menu.Item>
    </Menu>
  );

  const dropdownGroup = (
    <span className={styles.iconGroup}>
      <Dropdown overlay={menu} placement="bottomRight">
        <EllipsisOutlined/>
      </Dropdown>
    </span>
  );

  const handleChangeSalesType = (e: RadioChangeEvent) => {
    setSalesType(e.target.value);
  };


  return (
    <WrapContent>
      <GridContent>
        <>
          <Row
            gutter={24}
          >
            <Col xl={24} lg={24} md={24} sm={24} xs={24}>
              <Suspense fallback={null}>
                <IntroduceRow
                  loading={loading}
                  instanceTaskCountData={instanceTaskCountData as InstanceTaskCountData || {}}
                  dashboardData={dashboardData as DashboardData || {}}
                  plateNoSummaryData={plateNoSummaryData as InstancePlateNoSummaryData || {}}
                />
              </Suspense>
            </Col>
          </Row>
          <Row
            gutter={24}
            style={{
              marginTop: 24,
            }}
          >
            <Col xl={12} lg={24} md={24} sm={24} xs={24}>
              <Suspense fallback={null}>
                <TopSearch
                  loading={loading}
                  visitData2={resourceList || []}
                  searchData={resourceList || []}
                  dropdownGroup={dropdownGroup}
                />
              </Suspense>
            </Col>
            <Col xl={12} lg={24} md={24} sm={24} xs={24}>
              <Suspense fallback={null}>
                <ProportionSales
                  dropdownGroup={dropdownGroup}

                  loading={loading}
                  data={plateNoData || []}
                  handleChangeSalesType={handleChangeSalesType}
                />
              </Suspense>
            </Col>
          </Row>

          <Row
            gutter={24}
            style={{
              marginTop: 24,
            }}
          >

            <Col xl={24} lg={24} md={24} sm={24} xs={24}>
              <Card style={{width: "100%"}} title={"相关链接"}>
                <Descriptions>
                  {

                    dashboardData && dashboardData.links.map((item, index) => {
                      return (<Descriptions.Item span={12}>{index + 1}.&nbsp;&nbsp;<a href={item.url}
                                                                                      target={"_blank"}>{item.linkName}</a></Descriptions.Item>)
                    })
                  }
                </Descriptions>
              </Card>
            </Col>
          </Row>

        </>
      </GridContent>
    </WrapContent>
  );
};

export default Analysis;
