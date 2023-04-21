package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceCapDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.InstanceCapDTOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinyse
 * @description 实例板控制器
 */
@RestController
@RequestMapping("/instanceCap")
@RequiredArgsConstructor
@Api(tags = "开盖机(InstanceCap)")
@ApiSupport(order = 90)
public class InstanceCapController {

    private final InstanceCapDTOService instanceCapDTOService;


    @GetMapping(value = "/capDynamic")
    @ApiOperation(value = "查询开盖机",notes = "获取正在运行中的开关盖机信息",response = InstanceCapDTO.class)
    public ApiResponseInterface<List<InstanceCapDTO>> plateDynamic() {

        return ApiSuccessResponse.of(instanceCapDTOService.listInstanceCapByOpen());
    }

}
