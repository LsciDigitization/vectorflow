import {Link} from "umi";
import {BookOutlined, LinkOutlined} from "@ant-design/icons";

const devHelpLinks = [
  <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
    <LinkOutlined/>
    <span>OpenAPI 文档</span>
  </Link>,
  <Link key="docs" to="/~docs">
    <BookOutlined/>
    <span>业务组件文档</span>
  </Link>
];

export default devHelpLinks;
