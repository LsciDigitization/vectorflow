package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.PipetteStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.PipetteSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.PipetteSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePipetteEntity;
import com.mega.hephaestus.pms.data.mysql.service.ILabwarePipetteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PipetteDTOService {

    private final ILabwarePipetteService pipetteService;

    /**
     * 分页查询
     *
     * @param dto 查询条件
     * @return 分页数据
     */
    public PageResult<PipetteSearchResultDTO> listSearchResult(PipetteSearchRequestDTO dto) {

        Page<LabwarePipetteEntity> page = new Page<>(dto.getPageNum(),
                dto.getPageSize());

        LambdaQueryWrapper<LabwarePipetteEntity> queryWrapper = new LambdaQueryWrapper<>();


        IPage<LabwarePipetteEntity> entityIPage = pipetteService.page(page, queryWrapper);

        List<LabwarePipetteEntity> entityList = entityIPage.getRecords();

        List<PipetteSearchResultDTO> result = PipetteStructMapper.INSTANCE.entity2SearchResultDto(entityList);

        return PageResult.<PipetteSearchResultDTO>builder().list(result)
                .count(entityIPage.getTotal())
                .totalPage(entityIPage.getPages()).currentPage(entityIPage.getCurrent())
                .pageSize(entityIPage.getSize())
                .build();
    }


}
