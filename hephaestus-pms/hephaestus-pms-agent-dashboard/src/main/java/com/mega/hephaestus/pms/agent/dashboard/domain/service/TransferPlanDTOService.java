package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.component.utils.exception.CustomException;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProjectDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.ProcessModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.TransferPlanStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.TransferSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferPlanSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareLiquidEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferPlanEntity;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareLiquidService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTransferPlanService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferPlanDTOService {


    private final ProjectDTOManager projectDTOManager;

    private final ILabwareTransferPlanService transferPlanService;

    private final ILabwareLiquidService liquidService;

    /**
     * 分页查询
     *
     * @param dto 查询条件
     * @return 分页数据
     */
    public PageResult<TransferPlanSearchResultDTO> listSearchResult(TransferSearchRequestDTO dto) {

        Optional<ProcessModel> currentProcess = projectDTOManager.getCurrentProcess();
        if (currentProcess.isPresent()) {

            Page<LabwareTransferPlanEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());

            LambdaQueryWrapper<LabwareTransferPlanEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LabwareTransferPlanEntity::getIsDeleted, BooleanEnum.NO)
                        .eq(LabwareTransferPlanEntity::getProjectId, currentProcess.get().getProjectId());

            IPage<LabwareTransferPlanEntity> entityIPage = transferPlanService.page(page, queryWrapper);

            List<LabwareTransferPlanEntity> entityList = entityIPage.getRecords();

            List<TransferPlanSearchResultDTO> result = transformTransferPlan(entityList);

            return PageResult.<TransferPlanSearchResultDTO>builder().list(result).count(entityIPage.getTotal()).totalPage(entityIPage.getPages()).currentPage(entityIPage.getCurrent()).pageSize(entityIPage.getSize()).build();
        }

        return new PageResult<>();
    }


    public TransferPlanSearchResultDTO details(long id) {

        LabwareTransferPlanEntity labwareTransferPlan = transferPlanService.getById(id);

        if (Objects.nonNull(labwareTransferPlan)) {
            Optional<TransferPlanSearchResultDTO> resultDTO = transformTransferPlan(labwareTransferPlan);
            if (resultDTO.isPresent()) {
                return resultDTO.get();
            }
        }

        throw new CustomException("移液计划不存在");
    }

    private Optional<TransferPlanSearchResultDTO> transformTransferPlan(LabwareTransferPlanEntity labwareTransferPlan) {
        ArrayList<LabwareTransferPlanEntity> arrayList = new ArrayList();
        arrayList.add(labwareTransferPlan);
        List<TransferPlanSearchResultDTO> transferPlanSearchResultDTOS = transformTransferPlan(arrayList);
        if (CollectionUtils.isNotEmpty(transferPlanSearchResultDTOS)) {
            return Optional.of(transferPlanSearchResultDTOS.get(0));
        }
        return Optional.empty();
    }

    private List<TransferPlanSearchResultDTO> transformTransferPlan(List<LabwareTransferPlanEntity> list) {
        if (CollectionUtils.isNotEmpty(list)) {

            // 相同属性映射
            List<TransferPlanSearchResultDTO> result = TransferPlanStructMapper.INSTANCE.entity2SearchResultDto(list);

            List<Long> collect = list.stream()
                    .filter(v -> Objects.nonNull(v.getLiquidId()) && v.getLiquidId() > 0)
                    .map(LabwareTransferPlanEntity::getLiquidId).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(collect)) {
                List<LabwareLiquidEntity> labwareLiquidEntities = liquidService.listByIds(collect);
                Map<Long, List<LabwareLiquidEntity>> liquidMap = labwareLiquidEntities.stream().collect(Collectors.groupingBy(LabwareLiquidEntity::getId));
                // 填充液体名称
                return result.stream().peek(v -> {
                    Long liquidId = v.getLiquidId();
                    if (Objects.nonNull(liquidId) && liquidId > 0) {
                        if (liquidMap.containsKey(liquidId)) {
                            List<LabwareLiquidEntity> liquidList = liquidMap.get(liquidId);
                            if (CollectionUtils.isNotEmpty(liquidList)) {
                                v.setLiquidName(liquidList.get(0).getName());
                            }
                        }
                    }
                }).collect(Collectors.toList());
            } else {

                return result;
            }

        }
        return List.of();
    }
}
