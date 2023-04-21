package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiPageListResponse;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.PipetteSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.TransferSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.PipetteSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.PipetteDTOService;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.TransferDTOService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pipette")
@RequiredArgsConstructor
@ApiSupport(order = 1)
public class PipetteController {

    private final PipetteDTOService dtoService;

    @GetMapping("/items")
    @ApiOperation(value = "枪头列表", notes = "根据条件查询枪头列表", response = PipetteSearchResultDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<ApiPageListResponse.ApiPageList<PipetteSearchResultDTO>> list(PipetteSearchRequestDTO searchRequestDTO) {
        PageResult<PipetteSearchResultDTO> pageResult = dtoService.listSearchResult(searchRequestDTO);
        List<PipetteSearchResultDTO> list = pageResult.getList();
        return ApiPageListResponse.of(list, Integer.parseInt(pageResult.getCurrentPage().toString()), Integer.parseInt(pageResult.getCount().toString()));
    }

}
