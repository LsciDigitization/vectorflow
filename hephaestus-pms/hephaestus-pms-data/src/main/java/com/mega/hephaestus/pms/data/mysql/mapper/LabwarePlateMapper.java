package com.mega.hephaestus.pms.data.mysql.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.view.PlateNoCountView;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePlateEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 实例板池表
 *
 * @author xianming.hu
 */
@Mapper
public interface LabwarePlateMapper extends SuperMapper<LabwarePlateEntity> {

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
