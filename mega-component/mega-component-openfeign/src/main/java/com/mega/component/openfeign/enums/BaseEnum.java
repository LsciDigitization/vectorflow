package com.mega.component.openfeign.enums;

/**
 * @author 胡贤明
 */
public interface BaseEnum<K> {


    /**
     * 获取枚举key
     *@return k
     */
    K getCode();

    /**
     * 获取枚举value
     *@return label
     */
    String getLabel();
}
