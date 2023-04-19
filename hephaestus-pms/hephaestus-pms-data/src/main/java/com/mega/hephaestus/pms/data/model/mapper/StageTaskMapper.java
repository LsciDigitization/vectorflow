package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 任务表
 *
 * @author xianming.hu
 */
@Mapper
public interface StageTaskMapper extends SuperMapper<HephaestusStageTask> {
    List<HephaestusStageTask> findList(HephaestusStageTask entity);

    /**
     *  连接查询设备表
     * @param stageId  stageId
     * @return  List<HephaestusStageTask> 集合
     */
    List<HephaestusStageTask> findStageTaskLeftDevice(@Param("stageId") Long stageId);
}
