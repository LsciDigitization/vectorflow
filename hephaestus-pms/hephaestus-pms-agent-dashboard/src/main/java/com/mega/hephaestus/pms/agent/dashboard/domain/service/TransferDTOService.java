package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.utils.date.DateUtils;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProcessRecordDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.TransferStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.TransferSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareLiquidEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePlateEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareWellEntity;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareLiquidService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwarePlateService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTransferService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareWellService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferDTOService {

    private final ILabwareTransferService transferService;

    private final ILabwareWellService wellService;

    private final ILabwareLiquidService liquidService;

    private final ILabwarePlateService plateService;

    private final ProcessRecordDTOManager processRecordDTOManager;
    /**
     * 转移文案
     * 在这次移液操作中，从起始板（ID {1001}）的孔位（ID {2001}）中取出了{10}µL的样品（ID {3001}），并将其转移到目标板（ID {1002}）的孔位（ID {2002}）。
     * 移液操作使用了移液头（ID {4001}），并在{2023年4月10日10:30:00}完成。
     * 这次移液操作处理的是样品。
     * <p>
     */


    private static final String SAMPLTE_TRANSFER_DSSCRIPTION = "在这次移液操作中，从起始板（ID %s）的孔位（ID %s）中取出了%sµL的样品（ID %s），并将其转移到目标板（ID %s）的孔位（ID %s）。" + "移液操作使用了移液头（ID %s），并在%s完成。" + "这次移液操作处理的是样品。";


    /**
     * <p>
     * 加液文案
     * 在这次移液操作中，将7µL的{上清}液体（ID {3003}）加入到目标板（ID {1003}）的孔位（ID {2003}）。
     * 移液操作使用了移液头（ID {4001}），并在{2023年4月10日10:40:00}完成。
     * 这次移液操作处理的是液体。
     */
    private static final String LIQUID_TRANSFER_DSSCRIPTION = "在这次移液操作中，将7µL的%s液体（ID %s）加入到目标板（ID %s）的孔位（ID %s）。" + "移液操作使用了移液头（ID %s），并在%s完成。" + "这次移液操作处理的是液体。";


    /**
     * 分页查询
     *
     * @param dto 查询条件
     * @return 分页数据
     */
    public PageResult<TransferSearchResultDTO> listSearchResult(TransferSearchRequestDTO dto) {

        Page<LabwareTransferEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        LambdaQueryWrapper<LabwareTransferEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LabwareTransferEntity::getIsDeleted, BooleanEnum.NO);

        IPage<LabwareTransferEntity> entityIPage = transferService.page(page, queryWrapper);

        List<LabwareTransferEntity> entityList = entityIPage.getRecords();

        List<TransferSearchResultDTO> result = transformLabwareTransfers(entityList);

        return PageResult.<TransferSearchResultDTO>builder().list(result).count(entityIPage.getTotal()).totalPage(entityIPage.getPages()).currentPage(entityIPage.getCurrent()).pageSize(entityIPage.getSize()).build();
    }


    /**
     * 根据液体id查询移动液体记录
     *
     * @param liquidId 液体id
     * @return 移液记录
     */
    public List<TransferSearchResultDTO> getByLiquidId(long liquidId) {


        List<LabwareTransferEntity> labwareTransferEntities = transferService.listByLiquidId(liquidId);


        return TransferStructMapper.INSTANCE.entity2SearchResultDto(labwareTransferEntities);
    }


    /**
     * 根据孔id 查询移液记录
     * 1、查询孔对应的样本id 和 液体id
     *
     * @param wellId 孔id
     * @return 移液记录
     */
    public List<TransferSearchResultDTO> getByWellId(long wellId) {
        LabwareWellEntity labwareWell = wellService.getById(wellId);
        if (Objects.nonNull(labwareWell)) {
            List<LabwareTransferEntity> all = getLabwareTransferEntities(labwareWell);
            //
            if (CollectionUtils.isNotEmpty(all)) {
                // 数据处理
                List<TransferSearchResultDTO> result = transformLabwareTransfers(all);

                final AtomicInteger i = new AtomicInteger(1);
                // 增加序号
                result.forEach(v -> {
                    v.setTransferDescription(i + "、" + v.getTransferDescription());
                    i.getAndIncrement();
                });
                return result;
            }

            return List.of();
        }
        return List.of();
    }


    /**
     * 移液计划实体处理成dto
     *
     * @param transferList 移液计划
     * @return 移液记录
     */
    private List<TransferSearchResultDTO> transformLabwareTransfers(List<LabwareTransferEntity> transferList) {

        // 拿到所有的板
        List<LabwarePlateEntity> labwarePlateEntities = getLabwarePlateEntities(transferList);
        // 板按照id进行分组
        Map<Long, List<LabwarePlateEntity>> plateMap = labwarePlateEntities.stream().collect(Collectors.groupingBy(LabwarePlateEntity::getId));

        // 获取到所有的 液体
        List<LabwareLiquidEntity> labwareLiquidEntities = getLabwareLiquidEntities(transferList);
        Map<Long, List<LabwareLiquidEntity>> liquidMap = labwareLiquidEntities.stream().collect(Collectors.groupingBy(LabwareLiquidEntity::getId));

        // 拿到所有的孔
        List<LabwareWellEntity> labwareWellEntities = getLabwareWellEntities(transferList);
        Map<Long, List<LabwareWellEntity>> collect = labwareWellEntities.stream().collect(Collectors.groupingBy(LabwareWellEntity::getId));


        return transferList.stream().map(v -> {
            TransferSearchResultDTO dto = new TransferSearchResultDTO();
            dto.setId(v.getId());
            dto.setPlanId(v.getPlanId());

            // 处理起始板数据
            Long sourcePlate = v.getSourcePlate();
            if (Objects.nonNull(sourcePlate)) {
                dto.setSourcePlate(v.getSourcePlate());
                if (plateMap.containsKey(sourcePlate)) {
                    List<LabwarePlateEntity> sourcePlateList = plateMap.get(sourcePlate);
                    if (CollectionUtils.isNotEmpty(sourcePlateList)) {
                        dto.setSourcePlateKey(sourcePlateList.get(0).getPlateType());
                    }
                }
            }

            // 处理起始孔key
            Long sourceWell = v.getSourceWell();
            dto.setSourceWell(sourceWell);
            if (sourceWell > 0) {
                if (collect.containsKey(sourceWell)) {
                    List<LabwareWellEntity> sourceWellList = collect.get(sourceWell);
                    if (CollectionUtils.isNotEmpty(sourceWellList)) {
                        dto.setSourceWellKey(sourceWellList.get(0).getWellKey());
                    }
                }
            }


            // 处理目标板
            Long destinationPlate = v.getDestinationPlate();
            if (Objects.nonNull(destinationPlate)) {
                dto.setDestinationPlate(destinationPlate);
                if (plateMap.containsKey(destinationPlate)) {
                    List<LabwarePlateEntity> destinationPlateList = plateMap.get(destinationPlate);
                    if (CollectionUtils.isNotEmpty(destinationPlateList)) {
                        dto.setDestinationPlateKey(destinationPlateList.get(0).getPlateType());
                    }
                }
            }

            // 处理目标孔key
            Long destinationWell = v.getDestinationWell();
            dto.setDestinationWell(destinationWell);
            if (destinationWell > 0) {
                if (collect.containsKey(destinationWell)) {
                    List<LabwareWellEntity> destinationWellList = collect.get(destinationWell);
                    if (CollectionUtils.isNotEmpty(destinationWellList)) {
                        dto.setDestinationWellKey(destinationWellList.get(0).getWellKey());
                    }
                }
            }

            // 处理液体数据
            Long transferLiquid = v.getLiquidId();
            dto.setLiquidId(transferLiquid);
            if (Objects.nonNull(transferLiquid) && transferLiquid > 0) {
                if (liquidMap.containsKey(transferLiquid)) {
                    List<LabwareLiquidEntity> labwareLiquidList = liquidMap.get(transferLiquid);
                    if (CollectionUtils.isNotEmpty(labwareLiquidList)) {
                        dto.setLiquidName(labwareLiquidList.get(0).getName());
                    }
                }

            }

            dto.setSampleId(v.getSourceSampleId());
            dto.setTransferType(v.getTransferType());
            dto.setPipetteId(v.getPipetteId());
            dto.setVolume(v.getVolume());
            dto.setTransferTime(v.getTransferTime());
            // 移液描述
            String transferDescription = makeTransferDescription(dto);
            dto.setTransferDescription(transferDescription);
            return dto;
        }).sorted(Comparator.comparing(TransferSearchResultDTO::getTransferTime).reversed()).collect(Collectors.toList());
    }


    /**
     * 获取 所有的移液 液体+样本
     *
     * @param labwareWell 孔实体
     * @return 移液计划(包含液体 + 样本)
     */
    private List<LabwareTransferEntity> getLabwareTransferEntities(LabwareWellEntity labwareWell) {

        if (Objects.nonNull(labwareWell)) {
            return getLabwareTransferEntities(labwareWell.getSampleId(), labwareWell.getId());
        }
        return List.of();
    }

    private List<LabwareTransferEntity> getLabwareTransferEntities(long sampleId, long wellId) {
        Optional<ProcessRecordEntity> last = processRecordDTOManager.getLast();
        if(last.isPresent()){
            return transferService.listBySampleIdAndWellId(sampleId, wellId,last.get().getId());
        }
       return List.of();
    }

    /**
     * 移液描述文案
     *
     * @param v 移液数据
     * @return 移液描述
     */
    private String makeTransferDescription(TransferSearchResultDTO v) {
        String transferDescription;
        if ("sample".equals(v.getTransferType())) {
            transferDescription = String.format(SAMPLTE_TRANSFER_DSSCRIPTION,
                    v.getSourcePlate() + "（" + v.getSourcePlateKey() + "）",
                    v.getSourceWellKey(),
                    v.getVolume(),
                    v.getSampleId(),
                    v.getDestinationPlate() + "（" + v.getDestinationPlateKey() + "）",
                    v.getDestinationWellKey(),
                    v.getPipetteId(),
                    DateUtils.formatDateTime(v.getTransferTime()));

        } else {
            transferDescription = String.format(LIQUID_TRANSFER_DSSCRIPTION,
                    v.getLiquidName(),
                    v.getLiquidId(),
                    v.getDestinationPlate() + "（" + v.getDestinationPlateKey() + "）",
                    v.getDestinationWellKey(),
                    v.getPlanId(),
                    DateUtils.formatDateTime(v.getTransferTime()));
        }
        return transferDescription;
    }

    /**
     * 拿到移液记录中所有用到的孔
     *
     * @param all 移液记录
     * @return 孔数据
     */
    private List<LabwareWellEntity> getLabwareWellEntities(List<LabwareTransferEntity> all) {
        List<Long> sourceWellId = all.stream().map(LabwareTransferEntity::getSourceWell).collect(Collectors.toList());

        List<Long> destinationWellId = all.stream().map(LabwareTransferEntity::getDestinationWell).collect(Collectors.toList());

        List<Long> allWellId = new ArrayList<>();
        allWellId.addAll(sourceWellId);
        allWellId.addAll(destinationWellId);
        if (CollectionUtils.isNotEmpty(allWellId)) {
            // 查询移液过程中所用到的所有孔
            return wellService.listByIds(allWellId);
        }
        return List.of();
    }

    /**
     * 拿到移液记录中所有用到的液体
     *
     * @param all 移液记录
     * @return 液体数据
     */
    private List<LabwareLiquidEntity> getLabwareLiquidEntities(List<LabwareTransferEntity> all) {
        List<Long> liquidIds = all.stream().filter(v -> Objects.nonNull(v.getLiquidId()) && v.getLiquidId() > 0).map(LabwareTransferEntity::getLiquidId).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(liquidIds)) {
            // 查询移液过程中所用到的所有孔
            return liquidService.listByIds(liquidIds);
        }
        return List.of();
    }

    /**
     * 拿到移液记录中所有用到的板
     *
     * @param all 移液记录
     * @return 板数据
     */
    private List<LabwarePlateEntity> getLabwarePlateEntities(List<LabwareTransferEntity> all) {
        // 所有的起始板ID
        List<Long> sourcePlateIds = all.stream().map(LabwareTransferEntity::getSourcePlate).collect(Collectors.toList());

        // 所有的目标板ID
        List<Long> destinationPlateIds = all.stream().map(LabwareTransferEntity::getDestinationPlate).collect(Collectors.toList());

        // 移液过程中 用到的所有板id
        List<Long> allPlateIds = new ArrayList<>();

        allPlateIds.addAll(sourcePlateIds);
        allPlateIds.addAll(destinationPlateIds);

        if (CollectionUtils.isNotEmpty(allPlateIds)) {
            // 所有的板
            return plateService.listByIds(allPlateIds);
        }
        return List.of();
    }


}
