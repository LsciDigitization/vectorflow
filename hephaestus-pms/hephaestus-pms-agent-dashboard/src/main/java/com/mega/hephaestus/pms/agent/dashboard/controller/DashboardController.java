package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.DashboardDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.DashboardDTOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Api(tags = "仪表盘(Dashboard)")
@ApiSupport(order = 10)
public class DashboardController {

    private final DashboardDTOService dashboardDTOService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "仪表盘",notes = "获取项目介绍、状态、链接等情况",response = DashboardDTO.class)
    @GetMapping
    public ApiResponseInterface<DashboardDTO> dashboard(){
        return ApiSuccessResponse.of(dashboardDTOService.dashboard());
    }
}
