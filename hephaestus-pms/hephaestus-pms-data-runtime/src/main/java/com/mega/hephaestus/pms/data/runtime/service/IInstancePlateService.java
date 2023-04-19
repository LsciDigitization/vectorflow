package com.mega.hephaestus.pms.data.runtime.service;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.model.view.PlateNoCountView;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstancePlate;

import java.util.List;
import java.util.Optional;


/**
 *
 *
 * @author xianming.hu
 */
public interface IInstancePlateService extends IBaseService<HephaestusInstancePlate> {

    /**
     * 根据池类型和是否消费  统计总数
     * @param historyId 实验组历史id
     * @param experimentPoolType 池类型
     * @param booleanEnum 是否消费
     * @return 数量
     */
     long countByHistoryIdPoolTypeAndIsConsumed(long historyId,String experimentPoolType, BooleanEnum booleanEnum);

    /**
     * 根据 实验组历史id、池类型和是否消费  获取板池
     * @param historyId 实验组历史id
     * @param experimentPoolType 池类型
     * @param booleanEnum 是否消费
     * @return 未消费板池
     */
     Optional<HephaestusInstancePlate> getByHistoryIdPoolTypeAndIsConsumed(long historyId, String experimentPoolType, BooleanEnum booleanEnum);


    /**
     * 根据 实验组历史id、池类型和是否消费  获取板池
     * @param historyId 实验组历史id
     * @param experimentPoolType 池类型
     * @param plateNo 板序号
     * @param booleanEnum 是否消费
     * @return 未消费板池
     */
    Optional<HephaestusInstancePlate> getByHistoryIdPoolTypeAndPlateNoAndIsConsumed(long historyId, String experimentPoolType,int plateNo, BooleanEnum booleanEnum);

    List<HephaestusInstancePlate> info(String experimentPoolType, Boolean isConsumed);

    /**
     *  根据实例id查询板
     * @param instanceId 实例id
     * @return    Optional<HephaestusInstancePlate>
     */
    Optional<HephaestusInstancePlate> getByInstanceId(long instanceId);

    List<HephaestusInstancePlate> getByInstanceIds(List<Long> instanceIds);


    /**
     * 根据实验组历史id 查询板列表
     * @param historyId 实验组历史id
     * @return 板列表
     */
    List<HephaestusInstancePlate> listByHistoryId(long historyId);

    /**
     *  按照板号分组统计
     * @return
     */
    List<PlateNoCountView> countGroupByPlateNo();

    /**
     *  按照板号分组统计完成的数量
     * @return
     */
    List<PlateNoCountView> countByIsFinishedGroupByPlateNo();
}

