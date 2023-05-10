import React from "react";
const RightWrapContent: React.FC = (props) => {
  return (
    <div style={{ width: '100%', float: 'right' }}>
      {props.children}
    </div>
  )
}

export default RightWrapContent;
