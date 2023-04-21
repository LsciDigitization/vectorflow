package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.ResourceTaskDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.DeviceTaskDTOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinyse
 */
@RestController
@RequestMapping("/deviceTask")
@RequiredArgsConstructor
@Api(tags = "资源任务(ResourceTask)")
@ApiSupport(order = 80)
public class DeviceTaskController {

    private final DeviceTaskDTOService deviceTaskDTOService;

    /**
     * 查询task列表
     */
    @GetMapping("/deviceTaskDynamic")
    @ApiOperation(value = "获取资源任务",notes = "获取正在运行中的资源任务信息",response = ResourceTaskDTO.class)
    public ApiResponseInterface< List<ResourceTaskDTO>> deviceTaskDynamic() {
        return ApiSuccessResponse.of(deviceTaskDTOService.deviceTaskDynamic());
    }

}
