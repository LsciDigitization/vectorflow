package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.*;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.InstancePlateDTOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author yinyse
 */
@RestController
@RequestMapping("/instancePlate")
@RequiredArgsConstructor
@Api(tags = "板(InstancePlate)")
@ApiSupport(order = 50)
public class InstancePlateController {

    private final InstancePlateDTOService instancePlateDTOService;


    @GetMapping(value = "/plateDynamic")
    @ApiOperation(value = "查询板动态",notes = "获取正在运行中的板动态信息",response = HephaestusInstanceDynamicDTO.class)
    public ApiResponseInterface<Map<String, List<InstancePlateDynamicDTO>>> plateDynamic() {

        return ApiSuccessResponse.of(instancePlateDTOService.labwareDynnamic());
    }

    @GetMapping(value = "/countRunningConsumables")
    @ApiOperation(value = "统计板信息",notes = "统计正在运行中的板信息，对总数、完成数、消费数的统计",response = PlateCountDTO.class)
    public ApiResponseInterface<PlateCountDTO> count() {

        return ApiSuccessResponse.of(instancePlateDTOService.countRunningConsumables());
    }

    @GetMapping(value = "/iterationMetrics")
    @ApiOperation(value = "通量信息统计",notes = "统计正在运行中的板信息，对每一个板号的总数、完成数、完成率进行统计",response = InstancePlateNoCountDTO.class)
    public ApiResponseInterface<List<InstancePlateNoCountDTO>> iterationMetrics() {

        return ApiSuccessResponse.of(instancePlateDTOService.iterationMetrics());
    }
}
