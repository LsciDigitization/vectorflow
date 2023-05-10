import {Settings as LayoutSettings} from '@ant-design/pro-layout';

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
  tabsLayout?: boolean;
  apiBasePath?: string;
} = {
  navTheme: 'light',
  headerTheme: 'light',
  primaryColor: '#722ED1',
  layout: 'mix',
  splitMenus: true,
  contentWidth: 'Fluid',
  fixedHeader: true,
  fixSiderbar: true,
  colorWeak: false,
  title: 'BioLAuto',
  pwa: false,
  logo: '/logo.png',
  iconfontUrl: '',
  tabsLayout: true,
  apiBasePath: '/api', // /api
};

export default Settings;
