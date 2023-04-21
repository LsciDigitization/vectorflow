package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.SampleStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.SampleSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.SampleDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.SampleSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareSampleEntity;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareSampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleDTOService {

    private final ILabwareSampleService sampleService;

    /**
     * 分页查询
     *
     * @param dto 查询条件
     * @return 分页数据
     */
    public PageResult<SampleSearchResultDTO> listSearchResult(SampleSearchRequestDTO dto) {

        Page<LabwareSampleEntity> page = new Page<>(dto.getPageNum(),
                dto.getPageSize());

        LambdaQueryWrapper<LabwareSampleEntity> queryWrapper = new LambdaQueryWrapper<>();


        IPage<LabwareSampleEntity> entityIPage = sampleService.page(page, queryWrapper);

        List<LabwareSampleEntity> entityList = entityIPage.getRecords();

        List<SampleSearchResultDTO> result = SampleStructMapper.INSTANCE.entity2SearchResultDto(entityList);

        return PageResult.<SampleSearchResultDTO>builder().list(result)
                .count(entityIPage.getTotal())
                .totalPage(entityIPage.getPages()).currentPage(entityIPage.getCurrent())
                .pageSize(entityIPage.getSize())
                .build();
    }



    public SampleDTO details(long id){

        LabwareSampleEntity entity = sampleService.getById(id);
        SampleDTO sampleDTO = SampleStructMapper.INSTANCE.entity2SampleDTO(entity);

        return sampleDTO;
    }
}
