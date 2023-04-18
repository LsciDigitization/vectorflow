package com.mega.component.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自动填充
 */
public class DateMetaObjectHandler implements MetaObjectHandler {
    private final MybatisPlusAutoFillProperties autoFillProperties;

    public DateMetaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
        this.autoFillProperties = autoFillProperties;
    }

    @Override
    public boolean openInsertFill() {
        return MetaObjectHandler.super.openInsertFill();
    }

    @Override
    public boolean openUpdateFill() {
        return MetaObjectHandler.super.openUpdateFill();
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Object isDeleted = getFieldValByName(autoFillProperties.getIsDeletedField(), metaObject);



        if (isDeleted == null) {
            setFieldValByName(autoFillProperties.getIsDeletedField(), BooleanEnum.NO, metaObject);
        }

        setFieldValByName(autoFillProperties.getCreateTimeField(), new Date(), metaObject);
        setFieldValByName(autoFillProperties.getUpdateTimeField(), new Date(), metaObject);
        String userId = this.getUserId();
        if (userId != null) {
            setFieldValByName(autoFillProperties.getCreateByField(), userId, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(autoFillProperties.getUpdateTimeField(), new Date(), metaObject);
        Object isDeleted = getFieldValByName(autoFillProperties.getIsDeletedField(), metaObject);

        String userId = this.getUserId();
        if (userId != null) {
            setFieldValByName(autoFillProperties.getUpdateByField(), userId, metaObject);
        }

        if (isDeleted == null) {
            setFieldValByName(autoFillProperties.getIsDeletedField(), BooleanEnum.NO, metaObject);
        }
    }

    public String getUserId() {
        return "-1";
    }
}
