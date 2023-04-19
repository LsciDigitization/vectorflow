package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.view.PlateNoCountView;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstancePlate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 实例板池
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface InstancePlateMapper extends SuperMapper<HephaestusInstancePlate> {

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
