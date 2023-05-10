import React, {useEffect, useState} from 'react';

import {Form, List, Modal} from 'antd';

import {TransferData, TransferListProps} from "@/pages/hephaestus/dynamic/instanceIteration/holeData/transferData";
import {getTransferData} from "@/pages/hephaestus/dynamic/instanceIteration/holeData/transferService";

const TransferList: React.FC<TransferListProps> = (props) => {
  const [form] = Form.useForm();
  console.log(props)

  const {wellId,visible} = props


  const [transferList,setTransferList] = useState<TransferData[]>([]);
  useEffect(() => {
    if(wellId && visible){
      // 拿到id 去查询
      getTransferData(wellId).then(res =>{
        if(res.data){
          console.log("查询结果",res.data)
          setTransferList(res.data)
        }

      })
    }


  }, [form, props]);
  const handleOk = () => {
    form.submit();
  };
  const handleCancel = () => {
    props.onCancel();
   };


  // @ts-ignore
  return (
    <Modal
      width={980}
      // title={title}
      open={props.visible}
      destroyOnClose
      onOk={handleOk}
      onCancel={handleCancel}
    >
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
    </Modal>
  );
};
export default TransferList;
