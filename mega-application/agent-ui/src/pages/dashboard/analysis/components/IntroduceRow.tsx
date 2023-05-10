import {TinyArea, TinyColumn, Progress} from '@ant-design/charts';
import {Col, Row, Tag, Tooltip} from 'antd';

import numeral from 'numeral';
import {ChartCard, Field} from '../../../../components/Charts';
import type {DashboardData, DataItem, InstancePlateNoSummaryData} from '../data.d';
import styles from '../style.less';
import {InfoCircleOutlined} from "@ant-design/icons";
import {InstanceTaskCountData} from "../data.d";

const topColResponsiveProps = {
  xs: 24,
  sm: 12,
  md: 12,
  lg: 12,
  xl: 6
};

const IntroduceRow = ({loading, instanceTaskCountData, dashboardData, plateNoSummaryData}: {
  loading: boolean;
  instanceTaskCountData: InstanceTaskCountData;
  dashboardData: DashboardData,
  plateNoSummaryData: InstancePlateNoSummaryData
}) => {
  console.log("dashboardData", dashboardData)
  // @ts-ignore
  let instanceTaskTotal = instanceTaskCountData.total ?? 0
  // @ts-ignore
  let finishedTotal = instanceTaskCountData.finishedTotal ?? 0
  // @ts-ignore
  let runningTotal = instanceTaskCountData.runningTotal ?? 0
  let instanceTaskCompletionRate: string = "0"
  if (instanceTaskTotal > 0) {
    instanceTaskCompletionRate = (finishedTotal / instanceTaskTotal).toFixed(3)
  }

  // @ts-ignore
  let plateFinishedTotal = plateNoSummaryData.finishedTotal ?? 0
  // @ts-ignore
  let plateConsumedTotal = plateNoSummaryData.consumedTotal ?? 0
  // @ts-ignore
  let plateTotal = plateNoSummaryData.total ?? 0
  let plateCompletionRate = 0
  if (plateTotal > 0) {
    if (plateFinishedTotal == plateTotal) {
      plateCompletionRate = 100
    } else {
      plateCompletionRate = Number((plateFinishedTotal / plateTotal).toFixed(2))

    }
  }

  let durationTime = plateNoSummaryData.durationTimeFormat ?? ''

  return (
    <Row gutter={24}>
      <Col {...topColResponsiveProps}>
        <ChartCard
          bordered={false}
          title="项目介绍"
          action={
            <Tooltip title={dashboardData.projectDescription}>
              <InfoCircleOutlined/>
            </Tooltip>
          }
          loading={loading}
          total={() => dashboardData.projectName}
          footer={<Tag color="#87d068">{dashboardData.projectStatus}</Tag>}
          contentHeight={46}
        >
          <TinyArea
            color="#975FE4"
            xField="x"
            height={46}
            forceFit
            yField="y"
            smooth
            // data={visitData}
          />
        </ChartCard>
      </Col>

      <Col {...topColResponsiveProps}>
        <ChartCard
          bordered={false}
          loading={loading}
          title="实验板数"
          action={
            <Tooltip title="实验所需要的板子数量">
              <InfoCircleOutlined/>
            </Tooltip>
          }
          // @ts-ignore
          total={numeral(plateTotal).format('0,0') + "块板"}
          footer={<Field label="完成率" value={plateCompletionRate + "%"}/>}
          contentHeight={46}
        >

          已完成 <span className={styles.trendText}>{plateFinishedTotal + '块板 '}</span>
          已消费 <span className={styles.trendText}>{plateConsumedTotal + '块板'}</span>

        </ChartCard>
      </Col>
      <Col {...topColResponsiveProps}>
        <ChartCard
          bordered={false}
          loading={loading}
          title="任务总数"
          action={
            <Tooltip title="已分配任务数">
              <InfoCircleOutlined/>
            </Tooltip>
          }
          total={numeral(instanceTaskTotal).format('0,0')}
          footer={<Field label="完成率" value={instanceTaskCompletionRate + '%'}/>}
          contentHeight={46}
        >
          <TinyColumn xField="x" height={46} forceFit yField="y"/>
        </ChartCard>
      </Col>
      <Col {...topColResponsiveProps}>
        <ChartCard
          loading={loading}
          bordered={false}
          title="项目完成百分比"
          // action={
          //   <Tooltip title="指标说明">
          //     <InfoCircleOutlined/>
          //   </Tooltip>
          // }
          total={plateCompletionRate + "%"}
          // footer={
          //   <div style={{ whiteSpace: 'nowrap', overflow: 'hidden' }}>
          //     <Trend flag="up" style={{ marginRight: 16 }}>
          //       周同比
          //       <span className={styles.trendText}>12%</span>
          //     </Trend>
          //     <Trend flag="down">
          //       日同比
          //       <span className={styles.trendText}>11%</span>
          //     </Trend>
          //   </div>
          // }
          footer={<Field label="总耗时" value={durationTime}/>}

          contentHeight={46}
        >
          <Progress
            height={46}
            percent={plateCompletionRate}
            color="#13C2C2"
            forceFit
            size={8}
            marker={[
              {
                value: 0.8,
                style: {
                  stroke: '#13C2C2',
                },
              },
            ]}
          />
        </ChartCard>
      </Col>
    </Row>
  );

}
export default IntroduceRow;
