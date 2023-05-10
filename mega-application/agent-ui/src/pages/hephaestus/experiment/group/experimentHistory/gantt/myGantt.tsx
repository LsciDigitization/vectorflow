// @ts-ignore
import React, {useEffect, useState} from 'react';
// @ts-ignore
import './gantt.css';
import cx from 'classnames'
// @ts-ignore
import {getUrlParamsByKey, randomUUID} from "@/utils/utils";

import {Button, Card, Form, Select, Switch, Tag} from "antd";

import Gantt from "./index"
import dayjs from "dayjs";
import type {
  GanttRow,
  TaskItem
} from "@/pages/hephaestus/experiment/group/experimentHistory/gantt/ganttData";
import {
  getPlateTypeColorByRunning, getResourceColorByRunning, getRunningGantt
} from "@/pages/hephaestus/experiment/group/experimentHistory/gantt/service";

// 打印
const print = () => {
  const style = document.createElement("style")
  const width = (document.getElementsByClassName("gantt-blocks-wrapper")[0].scrollWidth + 350) + 'px'
  const heigth = (document.getElementsByClassName("gantt-blocks-wrapper")[0].scrollHeight + 500) + 'px'
  style.innerHTML = "@page{size: " + width + " " + heigth + "}"
  window.document.head.appendChild(style);
  window.print()
  window.document.head.removeChild(style)

}


// 设备type 对应class
const deviceTypeClassData = {
  ViaFill: 'ViaFill-class',
  XPeel: 'XPeel-class',
  PCR: 'PCR-class',
  SPEA200: 'SPEA200-class',
  Multidrop: 'Multidrop-class',
  LabelPrinter: 'LabelPrinter-class',
  Scanner: 'Scanner-class',
  LabwareHandler: 'LabwareHandler-class',
  Star: 'Star-class',
  Sealer: 'Sealer-class',
  Incubator: 'Incubator-class',
  LPX440: 'LPX440-class',
  IntelliXcap: 'IntelliXcap-class',
  STX220: 'STX220-class',
  ShakePool: 'ShakePool-class',
  Centrifuge: 'Centrifuge-class',
  None: 'None-class'
}
// 板颜色
const poolTypeColor = {
  // 样品
  'sample': {
    "dark": "rgb(0,255,0,0.8)",
    "light": "rgb(0,255,0,0.1)"
  },
  // 标品
  'standard': {
    "dark": "rgb(255,255,0,0.8)",
    "light": "rgb(255,255,0,0.1)"
  },
  // 空板子
  'empty': {
    "dark": "rgb(192,192,192,0.8)",
    "light": "rgb(192,192,192,0.1)"
  },
  'pipette1': {
    "dark": "rgba(0,0,255,0.97)",
    "light": "rgb(0,0,255,0.1)"
  },
  'pipette2': {
    "dark": "rgb(0,255,255,0.8)",
    "light": "rgb(0,255,255,0.1)"
  }
}

// 刻度options
const scaleOptions = [
  {
    value: '1',
    label: '1分钟',
  },
  {
    value: '5',
    label: '5分钟',
  },
  {
    value: '15',
    label: '15分钟',
  },
  {
    value: '60',
    label: '1小时',
  },
  {
    value: '240',
    label: '4小时',
  },
  {
    value: '720',
    label: '12小时',
  },
  {
    value: '1440',
    label: '1天',
  },
]

const MyGantt: React.FC = () => {

  const [experimentGroupName,setExperimentGroupName] =useState<string>('')

  const [data, setData] = useState<GanttRow[]>([])
  const [scale, setScale] = useState<number>(5)
  const [start, setStart] = useState<string>()
  const [end, setEnd] = useState<string>()
  const [startFormat, setStartFormat] = useState<string>()
  const [endFormat, setEndFormat] = useState<string>()
  const [durationTimeFormat, setDurationTimeFormat] = useState<string>()

  const [plateColor, setPlateColor] = useState<any>()
  const [resourceColor, setResourceColor] = useState<any>()
  const [legendList, setLegendList] = useState<any[]>([])

  const [allGanttRow, setAllGanttRow] = useState<GanttRow[]>([])
  const [noPipetteGanttRow, setNoPipetteGanttRow] = useState<GanttRow[]>([])

  // 是否显示枪头 false 不显示枪头
  const [isShowPipette, setIsShowPipette] = useState<boolean>(false)
  const [buttonTitle, setButtonTitle] = useState<string>("显示枪头")

  // 是否显示设备号
  const [isShowDeviceIndex, setIsShowDeviceIndex] = useState<boolean>(false)

  //
  /** 国际化配置 */

    // 请求所有数据
  const getAllData = () => {
      setScale(5);

      let letPlateColor = {}
      let letResourceColor = {}
      getPlateTypeColorByRunning().then(res => {
        const plateList = res.data;
        if (plateList) {
          const opt = {}
          plateList.map((item: any) => {
            opt[item.poolKey] = item.color
          })
          setPlateColor(opt)
          letPlateColor = opt
        }
      })


      getResourceColorByRunning().then(res => {
        if(res.data){
          const list = []
          res?.data.map((item: any) => {
            // {
            //   deviceType: 'Centrifuge',
            //     deviceTypeName: '离心机',
            //   color: '#DA70D6',
            //   class: "Centrifuge-class"
            // },
            // list.push({
            //   deviceType: item.deviceType,
            //   deviceTypeName:item.deviceTypeName,
            //   color: item.color
            // })
            letResourceColor[item.deviceType] = {
              color: item.color,
              deviceTypeName: item.deviceTypeName,
              // background: item.color + " !important",
              // borderColor: item.color + " !important",
              // webkitPrintColorAdjust: "exact"
            }

          })
          for (let key in letResourceColor) {
            list.push({
              deviceType: key,
              deviceTypeName: letResourceColor[key].deviceTypeName,
              color: letResourceColor[key].color
            })
          }

          setLegendList(list)
          setResourceColor(letResourceColor)

        }

      })

      getRunningGantt().then(response => {
        if (response.data) {
          setExperimentGroupName(response.data.experimentGroupName)
          if (response.data.instances) {
            const instanceList = response.data.instances
//
            const instanceNewList: GanttRow[] = [];
            const instanceNoPipetteList: GanttRow[] = [];

            for (let i = 0; i < instanceList.length; i++) {
              const taskItems: TaskItem[] = []
              const instanceObj = instanceList[i]
              const arrayLength = instanceObj.tasks.length
              for (let j = 0; j < arrayLength; j++) {
                const id = randomUUID()
                taskItems.push({
                  id: id,
                  start: dayjs(dayjs(instanceObj.tasks[j].startTime).toDate()).toString(),
                  end: dayjs(dayjs(instanceObj.tasks[j].endTime).toDate()).toString(),
                  "name": instanceObj.tasks[j].taskName,
                  parentId: i,
                  deviceType: instanceObj.tasks[j].deviceType,
                  deviceKey: instanceObj.tasks[j].deviceKey,
                  color: instanceObj.tasks[j].ganttColor,
                  status: 100 // 状态
                })
              }
              let batchNo = ''
              if (instanceObj.batchNo) {
                batchNo = "P" + instanceObj.batchNo + "."
              }


              if (instanceObj.experimentPoolType.indexOf("pipette") == -1) {
                let noPipetteGanttRow: GanttRow = {
                  rawIndex: i,
                  colorPair: letPlateColor[instanceObj.experimentPoolType],
                  speed: 35,
                  gtArray: taskItems,
                  id: i,
                  leftTitle: batchNo + instanceObj.instanceName + "（" + instanceObj.experimentPoolTypeName + " " + instanceObj.plateNo + "）"
                }
                instanceNoPipetteList.push(noPipetteGanttRow)
                // instanceNoPipette
              }

              const ganttRow: GanttRow = {
                rawIndex: i,
                colorPair: letPlateColor[instanceObj.experimentPoolType],
                speed: 35,
                gtArray: taskItems,
                id: i,
                leftTitle: batchNo + instanceObj.instanceName + "（" + instanceObj.experimentPoolTypeName + " " + instanceObj.plateNo + "）"
              }
              instanceNewList.push(ganttRow)

            }

            if (instanceList) {
              setStart(response.data.startTime)
              setEnd(response.data.endTime)
              setStartFormat(response.data.startTimeFormat)
              setEndFormat(response.data.endTimeFormat)
              setDurationTimeFormat(response.data.durationTimeFormat)
            }

            setAllGanttRow(instanceNewList)
            setNoPipetteGanttRow(instanceNoPipetteList)

            if (isShowPipette) {
              setData(instanceNewList)
            } else {
              setData(instanceNoPipetteList)
            }
          }
        }
      })


//
    }

  useEffect(() => {

    getAllData()

  }, []);

  // @ts-ignore
  const taskTitleStyle:DetailedHTMLProps<HTMLAttributes<HTMLDivElement>, HTMLDivElement> = (ganttRow: GanttRow) => {
    return {
      background: ganttRow.colorPair,
      WebkitPrintColorAdjust: 'exact'
    }
  }

  // 格式化设备序号 例如ShakePool-7 输出 7
  const formatDeviceIndex = (deviceKey: string) => {
    if (isShowDeviceIndex) {
      if (deviceKey) {
        if (deviceKey.indexOf("-") > -1) {
          const split = deviceKey.split("-")
          return split[1]
        }
        return deviceKey
      }
      return 1
    }
    return ""
  }


  const formatDeviceTypeClass = (deviceType: string) => {

    return resourceColor[deviceType]
  }


  function GanttLeft(ganttRow: GanttRow) {



    // @ts-ignore
    return (
      <div className="name" style={taskTitleStyle(ganttRow)}>
        <div className="colorBar" style={taskTitleStyle(ganttRow)}/>
        <div className="carId" style={{fontSize: '10px'}}>{ganttRow.leftTitle}</div>
      </div>

      // <div className="myleftblock">{data.name}</div>
    )
  }

  function TestBlock(item: any) {
    const planCss = "plan";
    const color = item.color
    // const taskClass = cx(planCss, color)
    // "color": item.color + ' !important',
    //   "background": item.color + ' !important',
    //   "borderColor": item.color + ' !important',
    //   webkitPrintColorAdjust: "exact"
    return (
      <div className={planCss}
           style={
             {
               background: item.color,
               borderColor: item.color,
               color: item.color,
               WebkitPrintColorAdjust: 'exact'
             }
           }>
        <div style={{display: "flex", flexDirection: "column"}}>
          <span style={{color: "black",}}>{formatDeviceIndex(item.deviceKey)}</span>
        </div>
      </div>
    )
  }

  function GanttRight(ganttRow: GanttRow,
                      getPositonOffset: (date: string) => number,
                      getWidthAbout2Times: (time1: string, time2: string) => number,
                      isInRenderingTimeRange: (time: string) => boolean,
                      startTimeOfRenderArea: number,
                      endTimeOfRenderArea: number) {
// `getPositonOffset(time:string):number `
// 定位函数，根据给定字符串形式的时间生成相对时间轴起点的的偏移值

// `getWidthAbout2Times(start:string,end:string):number`
// 为宽度计算函数，根据给定字符串形式的时间计算两个时间差的宽度值

// `isInRenderingTimeRange(time:string):boolean`
// 判定给定的时间是否在屏幕显示的时间轴范围之内

// startTimeOfRenderArea 屏幕当前显示范围的开始时间的毫秒数

// endTimeOfRenderArea 屏幕当前显示范围的结束时间的毫秒数

    return ganttRow.gtArray.map((item: TaskItem) => {

      return (
        <div className="gantt-block-item"
             key={item.id}
             style={{
               position: `absolute`,
               left: getPositonOffset(item.start) + 'px',
               width: getWidthAbout2Times(item.start, item.end) + 'px'
             }}>
          {TestBlock(item)}
        </div>
      )

    })
  }

  function MyHeader() {
    return (
      <div>{experimentGroupName}</div>
    )
  }

  const titleHeaderClass = cx("title-header", "no-print")
  // @ts-ignore
  return (

    <div
      style={{
        width: '100%',
        float: 'right',
        marginTop: '35px'
      }}
    >
      <div className={"gantt"} style={{overflow: "hidden"}}>
        <Card bordered={false}>
          <div className="title">
            <label className="title-header">流程：{experimentGroupName}</label>

            <label className={titleHeaderClass}>（开始时间：{startFormat}</label>
            <label className={titleHeaderClass}>&nbsp;&nbsp;&nbsp;&nbsp;结束时间：{endFormat}</label>
            <label className={titleHeaderClass}>&nbsp;&nbsp;&nbsp;&nbsp;耗时：{durationTimeFormat}）</label>

            <label className="item-label no-print">每刻度时长：</label>
            <Select
              className={"no-print"}
              defaultValue="5"
              style={{width: 240}}
              options={scaleOptions}
              onSelect={(value) => {
                setScale(parseInt(value))
              }}

            />
            {/*<Switch*/}
            {/*  style={{*/}
            {/*    marginBlockEnd: 16,*/}
            {/*  }}*/}
            {/*  checkedChildren="显示"*/}
            {/*  unCheckedChildren="隐藏"*/}
            {/*  onChange={(checked: boolean) =>{*/}
            {/*    console.log(checked)*/}

            {/*    if(checked){*/}
            {/*      setData(noPipetteGanttRow)*/}
            {/*    }else{*/}
            {/*      setData(allGanttRow)*/}
            {/*    }*/}
            {/*  }}*/}
            {/*/>*/}
            <Button
              className={"no-print"}
              style={{marginLeft: '12px'}}
              type="primary"
              onClick={() => {
                setData(allGanttRow)

                if (isShowPipette) {
                  setIsShowPipette(false)
                  setButtonTitle("显示枪头")
                  setData(noPipetteGanttRow)
                } else {
                  setIsShowPipette(true)
                  setButtonTitle("隐藏枪头")
                  setData(allGanttRow)
                }
              }}
            >
              {buttonTitle}
            </Button>
            <Button
              className={"no-print"}
              style={{marginLeft: '12px'}}
              type="primary"
              onClick={() => {
                setIsShowDeviceIndex(!isShowDeviceIndex)
              }}
            >
              {isShowDeviceIndex ? "隐藏设备号" : "显示设备号"}
            </Button>
            {/*<Button*/}
            {/*  className={"no-print"}*/}
            {/*  style={{marginLeft: '12px'}}*/}
            {/*  type="primary"*/}
            {/*  onClick={() => {*/}
            {/*    setData(noPipetteGanttRow)*/}
            {/*  }}*/}
            {/*>*/}
            {/*  隐藏枪头*/}
            {/*</Button>*/}
            <Button
              className={"no-print"}
              style={{marginLeft: '12px'}}
              type="primary"
              onClick={() => {
                getAllData()
              }}>
              刷新
            </Button>

            <Button
              className={"no-print"}
              type="primary"
              style={{marginLeft: '12px'}}
              onClick={() => {
                print()
              }}

            >
              打印
            </Button>
            {/*    <a-button className="btn no-print" type="primary" @click="refresh()" v-hasPermi="['hephaestusInstance:Instance:add']">*/}
            {/*    <a-icon type="sync-outlined"/>*/}
            {/*    刷新*/}
            {/*  </a-button>*/}

            {/*  <a-button class="btn no-print" type="primary" @click="print()" v-hasPermi="['hephaestusInstance:Instance:add']">*/}
            {/*  <a-icon type="sync-outlined"/>*/}
            {/*  打印*/}
            {/*</a-button>*/}
          </div>

        </Card>
        <Card bordered={false}>
          <div className="table-page-search-wrapper">
            <Form>
              {

                legendList?legendList.map((item: any) => {
                  // @ts-ignore
                  return (
                    <div style={{display: 'inline-block', marginBottom: '10px'}}>
                      {/* eslint-disable-next-line react/jsx-no-undef */}
                      <Tag style={{
                        marginLeft: '12px',
                        background: item.color,
                        // @ts-ignore
                        webkitPrintColorAdjust: 'exact'

                      }} className={item.class}>
                        &nbsp; &nbsp; &nbsp; &nbsp;
                      </Tag>
                      <span>{item.deviceTypeName}</span>
                    </div>
                  )
                }):<div></div>

              }
            </Form>

          </div>
        </Card>
        <div style={{height: `100vh`, width: '100vw'}}>
          <Gantt datas={data}
                 dataKey={"id"} // 最好传递key值，不然可能会出现闪动
                 startTime={start}
                 endTime={end}
                 scale={scale} // 刻度
                 cellWidth={60}
                 cellHeight={20}
                 titleHeight={40}
                 titleWidth={260}
            //render props
                 hideXScrollBar={false}
                 renderLeftBar={GanttLeft}
            // @ts-ignore
                 renderBlock={GanttRight}
                 renderHeader={MyHeader}/>
        </div>
      </div>
    </div>

  )
    ;
};
export default MyGantt;
