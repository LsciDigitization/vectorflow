package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;
import com.mega.hephaestus.pms.data.model.mapper.LabwarePlateTypeMapper;
import com.mega.hephaestus.pms.data.model.service.ILabwarePlateTypeService;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;


/**
 * @author xianming.hu
 */
@Slf4j
@Service
public class LabwarePlateTypeServiceImpl extends ServiceImpl<LabwarePlateTypeMapper, LabwarePlateTypeEntity> implements ILabwarePlateTypeService {


  /**
   * 根据资源id查询板类型
   *
   * @param resourceGroupId 资源组id
   * @return List<LabwarePlateTypeEntity>
   */
  @Override
  public List<LabwarePlateTypeEntity> listByResourceGroupId(long resourceGroupId) {
    List<LabwarePlateTypeEntity> list = lambdaQuery()
        .eq(LabwarePlateTypeEntity::getProjectId, resourceGroupId)
        .eq(LabwarePlateTypeEntity::getIsDeleted, BooleanEnum.NO)
        .list();

    if(CollectionUtils.isEmpty(list)){
      return List.of();
    }
    return list;
  }

  /**
   * 根据板key 查询板类型
   *
   * @param plateKey 板类型key
   * @return 板类型
   */
  @Override
  public Optional<LabwarePlateTypeEntity> getByPlateKey(String plateKey) {
    LabwarePlateTypeEntity one = lambdaQuery().eq(LabwarePlateTypeEntity::getPlateKey, plateKey).one();
    return Optional.ofNullable(one);
  }

  /**
   * 根据 key 批量查询
   *
   * @param keys keys
   * @return List<LabwarePlateTypeEntity>
   */
  @Override
  public List<LabwarePlateTypeEntity> listByKeys(Set<String> keys) {
    List<LabwarePlateTypeEntity> list = lambdaQuery()
            .in(LabwarePlateTypeEntity::getPlateKey, keys)
            .eq(LabwarePlateTypeEntity::getIsDeleted, BooleanEnum.NO)
            .list();

    if(CollectionUtils.isEmpty(list)){
      return List.of();
    }
    return list;
  }

  @Override
  public Optional<LabwarePlateTypeEntity> getByPlateKey(String plateKey, long projectId) {
    LabwarePlateTypeEntity one = lambdaQuery()
            .eq(LabwarePlateTypeEntity::getPlateKey, plateKey)
            .eq(LabwarePlateTypeEntity::getProjectId,projectId)
            .one();
    return Optional.ofNullable(one);
  }
}
