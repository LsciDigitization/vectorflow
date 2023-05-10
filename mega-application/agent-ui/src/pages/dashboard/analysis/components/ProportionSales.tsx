import {Card, Col, Progress, Radio, Row, Typography} from 'antd';
import type { RadioChangeEvent } from 'antd/es/radio';
import React from 'react';
import type { InstancePlateNoCountData} from '../data.d';
import styles from '../style.less';


const ProportionSales = ({
  loading,data
}: {
  loading: boolean;
  dropdownGroup: React.ReactNode;
  data: InstancePlateNoCountData[];
  handleChangeSalesType?: (e: RadioChangeEvent) => void;
}) =>{

   return (
    <Card
      loading={loading}
      className={styles.salesCard}
      bordered={false}
      title="实验通量"
      style={{
        height: '100%',
      }}
    >
      <div style={{
        height: 600,
        overflowY:"auto"
      }}>

        <>
          {
            data.map(item =>{
             return  (
               <Row  style={{height:50}}>
                 {/*<Col></Col>*/}
                 {/*<span style={{width:"50px"}}>{item.plateNo+'通量:'} </span>*/}
                 {/*<Progress percent={item.completionRate} strokeColor={{ '0%': '#108ee9', '100%': '#87d068' }}/>*/}
                 <Col span={2}>
                   {item.plateNo + '通量:'}
                 </Col>
                 <Col span={20}>
                   <Progress percent={item.completionRate} strokeColor={{'0%': '#108ee9', '100%': '#87d068'}}/>
                 </Col>
               </Row>
                 )
            })


          }

          {/* <div style={{display:"flex"}}>123<Progress percent={100}/></div>*/}
          {/*<Progress percent={0}  />*/}
        </>
      </div>
    </Card>
  );
}

export default ProportionSales;
