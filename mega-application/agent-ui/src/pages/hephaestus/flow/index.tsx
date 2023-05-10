import React, {useEffect, useState} from 'react';
import {useCallback} from 'react';
// @ts-ignore
import WrapContent from '@/components/WrapContent';
import ReactFlow, {
  Controls,
  useNodesState,
  useEdgesState,
  addEdge,
  Edge,
} from 'reactflow';
import {getEdgesData, getGraphData, getIterationGroup} from "@/pages/hephaestus/flow/service";
import 'reactflow/dist/style.css';
import './index.css';
import {jsPDF} from "jspdf";
import {toCanvas} from "html-to-image";
import {Button, Card, Col, Menu, Row, Spin} from "antd";

import {MenuItemType} from "antd/lib/menu/hooks/useItems";
import {StorageLeftMenuData} from "@/pages/hephaestus/resource/storage/data";
import {MenuFoldOutlined, MenuUnfoldOutlined} from '@ant-design/icons';



const mockLeftData = () => {
  let list = []
  for (let i = 1; i <= 4; i++) {
    list.push({
      title: "通量组" + i,
      label: "通量组" + i,
      key: "key-" + i
    } as StorageLeftMenuData)
  }
  let menuList = [
    {
      title: '通量组',
      label: '通量组',
      key: 'key',
      type: 'group',
      children: list
    }

  ]
  return menuList
}


const FlowPage: React.FC = () => {

  const [nodes, setNodes, onNodesChange] = useNodesState([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);

  const onConnect = useCallback((params) => setEdges((eds: Edge<any>[]) => addEdge(params, eds)), [setEdges]);

  const defaultViewport = {x: 100, y: 100, zoom: 1};

  const [leftMenuData, setLeftMenuData] = useState<StorageLeftMenuData[]>()

  const [currentMenu, setCurrentMenu] = useState('');


  const [collapsed, setCollapsed] = useState(false);

  // 左侧右侧 col 的布局大小
  const [leftColSpan, setLeftColSpan] = useState(4)
  const [rightColSpan, setRightColSpan] = useState(20)

  // loading
  const [loading,setLoading] = useState(true)

  const toggleCollapsed = () => {
    setCollapsed(!collapsed);
    if (!collapsed) {
      setLeftColSpan(2)
      setRightColSpan(22)
    } else {
      setLeftColSpan(4)
      setRightColSpan(20)
    }
  };

  const getFlowData = (batchNo: string) => {
    setLoading(true)
    setNodes([])
    setEdges([])
    getGraphData(batchNo).then(res => {
      if (res.data) {
        setNodes(res.data)
      }
    })
    getEdgesData(batchNo).then(res => {
      if (res.data) {
        setEdges(res.data)
      }
    })
    setTimeout(() => {
      setLoading(false)
    }, 1000);
    //

  }
  /** 国际化配置 */
  useEffect(() => {


    getIterationGroup().then(res => {
      if (res.data) {
        let list: MenuItemType[] = []
        res.data.map((item: any) => {
          list.push({
            title: item.name,
            label: item.name,
            key: item.batchNo
          } as MenuItemType)
        })
        let menuList = [
          {
            title: '通量组',
            label: '通量组',
            key: 'key',
            type: 'group',
            children: list
          }
        ]
        setLeftMenuData(menuList);
        let defaultKey = list[0].key+""
        onMenuClick(defaultKey);
        setCurrentMenu(defaultKey)
        getFlowData(defaultKey);
      }
    })


  }, []);


  const onMenuClick = (key: string) => {

    setCurrentMenu(key)
    getFlowData(key)
    // getDeviceNestData(key).then(res => {
    //   if (res.data) {
    //     setRightData(res.data);
    //   }
    //
    // })

  }
  const print = () => {
    const style = document.createElement("style")
    const width = (document.getElementsByClassName("react-flow")[0].scrollWidth + 300) + 'px'
    const heigth = (document.getElementsByClassName("react-flow")[0].scrollHeight + 300) + 'px'
    style.innerHTML = "@page{size: " + width + " " + heigth + "}"
    window.document.head.appendChild(style);
    window.print()
    window.document.head.removeChild(style)

  }
  const downloadPdf = () => {
    // @ts-ignore
    toCanvas(document.querySelector('#biol-react-flow'))
      .then(function (canvas) {

        let dataUrl = canvas.toDataURL()
        const doc = new jsPDF({
          orientation: "landscape",
          unit: "pt",
          format: [841.89, 595.28]
        });


        // @ts-ignore
        doc.html(dataUrl, {
          autoPaging: false,
          callback: function (doc) {
            // Save the PDF
            doc.save('sample-document.pdf');
          },
          x: 15,
          y: 15
        });
      });
  }


  const styleDefaults = {
    height: 100,
    color: "white",
    fontSize: 50,
    textAlign: "center"
  };

  return (
    <WrapContent>

      <div
        style={{width: '100%', height: '900px'}}
      >
        {/*<div style={{*/}
        {/*  display: "flex",*/}
        {/*  flexFlow: "row wrap",*/}
        {/*  minWidth: 0*/}

        {/*}}>*/}
        <Row gutter={[24,20]}>
          <Col span={leftColSpan}>
            <Card bordered={false} bodyStyle={{paddingLeft: "12px", paddingTop: "12px", paddingRight: "0px"}}>
              <Button onClick={toggleCollapsed} >
                {collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>}
              </Button>
              <Menu
                style={{height: "800px"}}
                selectedKeys={[currentMenu]}
                mode="inline"
                // inlineCollapsed={collapsed}
                items={leftMenuData as MenuItemType[]}
                onClick={({item, key, keyPath, domEvent}) => onMenuClick(key)}
              />
            </Card>
          </Col>

          <Col span={rightColSpan}>
            <Card bordered={false} style={{marginLeft:"8px"}} bodyStyle={{paddingLeft: "12px", paddingTop: "12px", paddingRight: "0px"}}>
              <Button className="download-btn no-print" onClick={print}>
                打印
              </Button>
              <Spin spinning={loading}>
              <div id="biol-react-flow" style={{width: '100%', height: '800px', overflow: "auto"}}>

                <ReactFlow
                  nodes={nodes}
                  edges={edges}
                  onNodesChange={onNodesChange}
                  onEdgesChange={onEdgesChange}
                  onConnect={onConnect}
                  // attributionPosition="top-right" // 这个是水印
                  defaultViewport={defaultViewport}
                >
                  <Controls className={"no-print"}></Controls>
                </ReactFlow>

              </div>
              </Spin>
            </Card>
          </Col>
        </Row>
        </div>




    </WrapContent>
  );
};
export default FlowPage;
