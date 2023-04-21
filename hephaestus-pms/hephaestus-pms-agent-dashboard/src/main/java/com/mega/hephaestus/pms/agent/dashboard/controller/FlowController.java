package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiPageListResponse;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.bioflow.flow.Edge;
import com.mega.component.bioflow.flow.FlatNode;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.FlowDTOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flow")
@RequiredArgsConstructor
@Api(tags = "工作流(Flow)")
@ApiSupport(order = 80)
public class FlowController {
    @Autowired
    private FlowDTOService dtoService;

    @GetMapping("/getFlatNode")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取node数据",notes = "获取当前项目的运行流程 node数据",response = FlatNode.class)
    public ApiResponseInterface<List<FlatNode>> getFlatNode() {
        return ApiPageListResponse.of(dtoService.getFlatNode());
    }

    @GetMapping("/getEdges")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取Edge数据",notes = "获取当前项目的运行流程 Edge数据",response = Edge.class)
    public ApiResponseInterface<List<Edge>> getEdges() {

        return ApiPageListResponse.of( dtoService.getEdges());
    }
}
