package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiPageListResponse;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.LiquidSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.LiquidSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.LiquidDTOService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/liquid")
@RequiredArgsConstructor
@ApiSupport(order = 1)
public class LiquidController {

    private final LiquidDTOService dtoService;

    @GetMapping("/items")
    @ApiOperation(value = "移液记录列表", notes = "根据条件查询移液记录列表", response = LiquidSearchResultDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<ApiPageListResponse.ApiPageList<LiquidSearchResultDTO>> list(LiquidSearchRequestDTO searchRequestDTO) {
        PageResult<LiquidSearchResultDTO> pageResult = dtoService.listSearchResult(searchRequestDTO);
        List<LiquidSearchResultDTO> list = pageResult.getList();
        return ApiPageListResponse.of(list, Integer.parseInt(pageResult.getCurrentPage().toString()), Integer.parseInt(pageResult.getCount().toString()));
    }

}
