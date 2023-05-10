import React from 'react';
// @ts-ignore
import * as allIcon from "@@/plugin-icons/";
import _ from "lodash";

const allIcons: Record<string, any> = allIcon;

// function firstUpperCase(str: string) {
//   return str.toLowerCase().replace(/( |^)[a-z]/g, (L) => L.toUpperCase());
// }

function getIconName(name: string) {
  // return firstUpperCase(name) + 'Icons';
  if(name.indexOf("Icons") >-1){
    return _.upperFirst(_.camelCase(name))
  }else{
    return _.upperFirst(_.camelCase(name))  + 'Icons';

  }

}
export function getIcon(name: string): React.ReactNode | string {
  const icon = allIcons[getIconName(name)];
  return icon || '';
}

export function createIcon(icon: string | any): React.ReactNode | string {
  if (typeof icon === 'object') {
    return icon;
  }
  const ele = allIcons[getIconName(icon)];
  if (ele) {
    return React.createElement(allIcons[getIconName(icon)]);
  }
  return '';
}
