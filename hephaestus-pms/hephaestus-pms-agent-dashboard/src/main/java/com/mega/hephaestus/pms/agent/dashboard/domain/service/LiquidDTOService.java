package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.LiquidStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.LiquidSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.LiquidSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareLiquidEntity;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareLiquidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiquidDTOService {

    private final ILabwareLiquidService liquidService;

    /**
     * 分页查询
     *
     * @param dto 查询条件
     * @return 分页数据
     */
    public PageResult<LiquidSearchResultDTO> listSearchResult(LiquidSearchRequestDTO dto) {

        Page<LabwareLiquidEntity> page = new Page<>(dto.getPageNum(),
                dto.getPageSize());

        LambdaQueryWrapper<LabwareLiquidEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LabwareLiquidEntity::getIsDeleted, BooleanEnum.NO);


        IPage<LabwareLiquidEntity> entityIPage = liquidService.page(page, queryWrapper);

        List<LabwareLiquidEntity> entityList = entityIPage.getRecords();

        List<LiquidSearchResultDTO> result = LiquidStructMapper.INSTANCE.entity2SearchResultDto(entityList);

        return PageResult.<LiquidSearchResultDTO>builder().list(result)
                .count(entityIPage.getTotal())
                .totalPage(entityIPage.getPages()).currentPage(entityIPage.getCurrent())
                .pageSize(entityIPage.getSize())
                .build();
    }


}
