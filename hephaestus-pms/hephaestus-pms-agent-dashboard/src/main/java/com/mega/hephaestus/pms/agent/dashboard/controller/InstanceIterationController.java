package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceIterationDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.InstanceIterationDTOService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yinyse
 */
@RestController
@RequestMapping("/instanceIteration")
@RequiredArgsConstructor
@Api(tags = "通量(instanceIteration)")
@ApiSupport(order = 50)
public class InstanceIterationController {

    private final InstanceIterationDTOService dtoService;

    @GetMapping(value = "/items")

    public ApiResponseInterface<List<InstanceIterationDTO>> items() {

        return ApiSuccessResponse.of(dtoService.list());
    }

}
