package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiPageListResponse;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.SampleSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.SampleDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.SampleSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.SampleDTOService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
@ApiSupport(order = 1)
public class SampleController {

    private final SampleDTOService dtoService;

    @GetMapping("/items")
    @ApiOperation(value = "样品列表", notes = "根据条件查询样品列表", response = SampleSearchResultDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<ApiPageListResponse.ApiPageList<SampleSearchResultDTO>> list(SampleSearchRequestDTO searchRequestDTO) {
        PageResult<SampleSearchResultDTO> pageResult = dtoService.listSearchResult(searchRequestDTO);
        List<SampleSearchResultDTO> list = pageResult.getList();
        return ApiPageListResponse.of(list, Integer.parseInt(pageResult.getCurrentPage().toString()), Integer.parseInt(pageResult.getCount().toString()));
    }


    @GetMapping("/{id}")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "demo详情", notes = "根据主键查询样品详情", response = SampleDTO.class)
    public ApiResponseInterface<SampleDTO> details(@PathVariable("id") Long id) {
        return ApiSuccessResponse.of(dtoService.details(id));
    }

}
