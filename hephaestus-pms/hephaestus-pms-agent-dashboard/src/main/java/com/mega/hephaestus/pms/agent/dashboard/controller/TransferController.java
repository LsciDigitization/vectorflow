package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiPageListResponse;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.mybatis.common.model.PageResult;
import com.mega.hephaestus.pms.agent.dashboard.domain.req.TransferSearchRequestDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.TransferDTOService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
@ApiSupport(order = 1)
public class TransferController {

    private final TransferDTOService dtoService;

    @GetMapping("/items")
    @ApiOperation(value = "移液记录列表", notes = "根据条件查询移液记录列表", response = TransferSearchResultDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<ApiPageListResponse.ApiPageList<TransferSearchResultDTO>> list(TransferSearchRequestDTO searchRequestDTO) {
        PageResult<TransferSearchResultDTO> pageResult = dtoService.listSearchResult(searchRequestDTO);
        List<TransferSearchResultDTO> list = pageResult.getList();
        return ApiPageListResponse.of(list, Integer.parseInt(pageResult.getCurrentPage().toString()), Integer.parseInt(pageResult.getCount().toString()));
    }

    @GetMapping("/getByLiquidId")
    @ApiOperation(value = "移液（液体id）记录列表", notes = "根据液体id查询移液记录列表", response = TransferSearchResultDTO.class)
    @ApiOperationSupport(order = 2)
    public ApiResponseInterface<List<TransferSearchResultDTO>> getByLiquidId(@RequestParam("liquidId") Long liquid) {
        List<TransferSearchResultDTO> result = dtoService.getByLiquidId(liquid);
        return ApiPageListResponse.of(result);
    }


    @GetMapping("/getByWellId")
    @ApiOperation(value = "移液（孔位id）记录列表", notes = "根据孔位id查询移液记录列表", response = TransferSearchResultDTO.class)
    @ApiOperationSupport(order = 3)
    public ApiResponseInterface<List<TransferSearchResultDTO>> getByWellId(@RequestParam("wellId") Long wellId) {
        List<TransferSearchResultDTO> result = dtoService.getByWellId(wellId);
        return ApiPageListResponse.of(result);
    }

}
