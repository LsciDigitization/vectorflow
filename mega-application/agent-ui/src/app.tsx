import type {Settings as LayoutSettings} from '@ant-design/pro-layout';
import {PageLoading} from '@ant-design/pro-layout';
import type {RunTimeLayoutConfig} from 'umi';
import {history} from 'umi';
import RightContent from '@/components/RightContent';
// import {BookOutlined, LinkOutlined} from '@ant-design/icons';
import defaultSettings from '../config/defaultSettings';
import {getUserInfo, getRoutersInfo} from '@/services/system/session';
import { isDev } from '@/utils/EnvUtils';
import devHelpLinks from "@/devHelp";

// const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/user/login';

/** 获取用户信息比较慢的时候会展示一个 loading */
export const initialStateConfig = {
  loading: <PageLoading/>,
};

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: API.CurrentUser;
  loading?: boolean;
  fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
}> {
  // const fetchUserInfo = async () => {
  //   try {
  //     const resp = await getUserInfo();
  //     if (resp === undefined || resp.code !== 200) {
  //       history.push(loginPath);
  //     } else {
  //       return {...resp.user, permissions: resp.permissions} as API.CurrentUser;
  //     }
  //   } catch (error) {
  //     history.push(loginPath);
  //   }
  //   return undefined;
  // };
  // 如果是登录页面，不执行
  // if (history.location.pathname !== loginPath) {
  //   const currentUser = await fetchUserInfo();
  //   return {
  //     settings: defaultSettings,
  //     currentUser,
  //     fetchUserInfo,
  //   };
  // }
  return {
    settings: defaultSettings,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({initialState, setInitialState}) => {
  return {
    rightContentRender: () => <RightContent/>,
    waterMarkProps: {
      content: initialState?.currentUser?.userName,
    },
    onPageChange: () => {
      // const {location} = history;
      // 如果没有登录，重定向到 login
      // if (!initialState?.currentUser && location.pathname !== loginPath) {
      //   history.push(loginPath);
      // }
    },
    // links: isDev ? devHelpLinks : [],
    links: isDev ? [] : [],
    menuHeaderRender: undefined,
    disableMobile: true,
    menu: {
      // 每当 initialState?.currentUser?.userid 发生修改时重新执行 request
      params: {
        userId: initialState?.currentUser?.id,
      },
      request: async () => {
        // console.log("userId", initialState?.currentUser?.id,)
        // if (!initialState?.currentUser?.id) {
        //   return [];
        // }
        // initialState.currentUser 中包含了所有用户信息
        const menus = await getRoutersInfo();
        console.log(menus)
        setInitialState((preInitialState) => ({
          ...preInitialState,
          menus,
        }));
        console.log('menus',menus)

        return menus;
      },
    },
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children) => {
      return (
        <div>
          {children}
          {/*{!props.location?.pathname?.includes('/login') && (*/}
          {/*  <SettingDrawer*/}
          {/*    enableDarkTheme*/}
          {/*    settings={initialState?.settings}*/}
          {/*    onSettingChange={(settings) => {*/}
          {/*      setInitialState((preInitialState) => ({*/}
          {/*        ...preInitialState,*/}
          {/*        settings,*/}
          {/*      }));*/}
          {/*    }}*/}
          {/*  />*/}
          {/*)}*/}
        </div>
      );
    },
    ...initialState?.settings,
  };
};
