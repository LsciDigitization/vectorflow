package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiPageListResponse;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.TransferSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferPlanSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.TransferDTOService;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.TransferPlanDTOService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transferPlan")
@RequiredArgsConstructor
@ApiSupport(order = 1)
public class TransferPlanController {

    private final TransferPlanDTOService dtoService;

    @GetMapping("/items")
    @ApiOperation(value = "移液计划列表", notes = "根据条件查询移液计划", response = TransferSearchResultDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<ApiPageListResponse.ApiPageList<TransferPlanSearchResultDTO>> list(TransferSearchRequestDTO searchRequestDTO) {
        PageResult<TransferPlanSearchResultDTO> pageResult = dtoService.listSearchResult(searchRequestDTO);
        List<TransferPlanSearchResultDTO> list = pageResult.getList();
        return ApiPageListResponse.of(list, Integer.parseInt(pageResult.getCurrentPage().toString()), Integer.parseInt(pageResult.getCount().toString()));
    }

    @GetMapping("/{id}")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "demo详情", notes = "根据主键查询移液计划详情", response = TransferPlanSearchResultDTO.class)
    public ApiResponseInterface<TransferPlanSearchResultDTO> details(@PathVariable("id") Long id) {
        return ApiSuccessResponse.of(dtoService.details(id));
    }

}
