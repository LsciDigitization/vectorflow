package com.mega.hephaestus.pms.agent.dashboard.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.PlateTypeColorDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.PlateTypeDTOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinyse
 */
@RestController
@RequestMapping("/resourcePlateType")
@RequiredArgsConstructor
@Api(tags = "板类型(PlateType)")
@ApiSupport(order = 40)
public class PlateTypeController {

    private final PlateTypeDTOService plateTypeDTOService;

    @GetMapping(value = "/plateTypeByRunning")
    @ApiOperation(value = "查询板类型",notes = "获取正在运行中的板类型信息",response = PlateTypeColorDTO.class)
    public ApiResponseInterface<List<PlateTypeColorDTO>> plateTypeColorByRunning() {
        return ApiSuccessResponse.of(plateTypeDTOService.plateTypeColorByRunning());
    }


}
