package com.mega.component.mybatis.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.component.mybatis.common.model.BaseDTO;
import com.mega.component.mybatis.common.service.IBaseService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 胡贤明
 */
public class BaseServiceImpl <M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {
    /**
     * 创建搜索查询
     * @param clazz clazz
     * @param baseDTO baseDTO
     * @return 无排序 无删除逻辑 QueryWrapper
     */
    public QueryWrapper<T> createQueryWrapperNoOrdeBy(Class<?> clazz, BaseDTO baseDTO) throws RuntimeException {
        return  builder(clazz,baseDTO);
    }


    /**
     * 创建搜索查询
     * 并且按照create_time 排序，is_deleted = 0；
     * @param clazz clazz
     * @param baseDTO baseDTO
     * @return  QueryWrapper
     */
    public QueryWrapper<T> createQueryWrapper(Class<?> clazz, BaseDTO baseDTO) throws RuntimeException {
        QueryWrapper<T> queryWrapper = builder(clazz,baseDTO);
        queryWrapper.eq("is_deleted", BooleanEnum.NO.getCode());
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }


    /**
     *
     * @param clazz clazz
     * @param baseDTO baseDTO
     * @return QueryWrapper QueryWrapper
     * @throws RuntimeException 异常
     */
    protected QueryWrapper<T> builder(Class<?> clazz , BaseDTO baseDTO) throws RuntimeException  {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        try {
            while (clazz != Object.class) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if ("serialVersionUID".equals(fieldName) || "currentPage".equals(fieldName) || "pageNum".equals(fieldName)
                            || "pageSize".equals(fieldName)) {
                        continue;
                    }
                    String upperChar = fieldName.substring(0, 1).toUpperCase();
                    String anotherStr = fieldName.substring(1);
                    String methodName = "get" + upperChar + anotherStr;
                    Method method = clazz.getMethod(methodName);
                    method.setAccessible(true);
                    Object fieldValue = method.invoke(baseDTO);
                    if (fieldValue != null && StringUtils.isNotBlank(String.valueOf(fieldValue))) {
                        if (field.getType().toString().equals("class java.lang.String")) {
                            queryWrapper.like(StringUtils.camelToUnderline(fieldName), fieldValue);
                        } else {
                            queryWrapper.eq(StringUtils.camelToUnderline(fieldName), fieldValue);
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return queryWrapper;
    }
}
