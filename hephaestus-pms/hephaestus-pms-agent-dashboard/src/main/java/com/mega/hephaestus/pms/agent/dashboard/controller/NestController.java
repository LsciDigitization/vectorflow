package com.mega.hephaestus.pms.agent.dashboard.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.DeviceNestGroupDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HoleDataDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.DeviceNestDTOService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 设备类型控制器
 *
 * @author xianming.hu
 */
@RestController
@RequestMapping("/nest")
@RequiredArgsConstructor
@ApiSupport(order = 1)
public class NestController {

    private final DeviceNestDTOService dtoService;
    /**
     * 设备类型列表
     */
    @GetMapping("/getNest")
    @ApiOperation(value = "获取设备存储信息", notes = "获取设备存储信息", response = HoleDataDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<List<DeviceNestGroupDTO>> getNest(@RequestParam("deviceKey") String deviceKey) {
        return ApiSuccessResponse.of( dtoService.listByDeviceKey(deviceKey));
    }



}
