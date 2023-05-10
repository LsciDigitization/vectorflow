import styles from './index.less';
import React from "react";

const WrapContent: React.FC = (props) => {
  return (
    <div className={styles.wraper}>{props.children}</div>
  )
};

export default WrapContent;
