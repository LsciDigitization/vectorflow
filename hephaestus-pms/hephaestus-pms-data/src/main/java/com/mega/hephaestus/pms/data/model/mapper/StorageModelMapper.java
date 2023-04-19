package com.mega.hephaestus.pms.data.model.mapper;


import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStorageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 存放模型表
 *
 * @author xianming.hu
 */
@Mapper
@Deprecated(since = "20221115")
public interface StorageModelMapper extends SuperMapper<HephaestusStorageModel> {
//    NucStorageModel getNucStorage(@Param("storageId")Long storageId);

    HephaestusStorageModel getHephaestusStorage(@Param("nucId")String nucId,@Param("nestGroupId")String nestGroupId,@Param("nest")Integer nest);
}
