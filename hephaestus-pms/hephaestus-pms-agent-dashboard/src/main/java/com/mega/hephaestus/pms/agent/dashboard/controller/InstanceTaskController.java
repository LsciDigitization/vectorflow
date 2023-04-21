package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.GanttChart;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceTaskCountDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceTaskDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceTaskInfo;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.InstanceTaskDTOService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mega.component.api.response.ApiResponseInterface;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


/**
 * 设备任务表
 *
 * @author xianming.hu
 */
@Slf4j
@RestController
@RequestMapping("/instanceTask")
@Api(tags = "实例任务(InstanceTask)")
@ApiSupport(order = 70)
public class InstanceTaskController {


    @Autowired
    private InstanceTaskDTOService instanceTaskDTOService;

    /**
     * 获取详情数据
     * @param instanceId 实例ID
     * @return 实例信息
     */
    @GetMapping(value = "/detail/{instanceId}")
    @ApiOperation(value = "查询任务详情",notes = "根据实例id获取实例详情，详情中包含该实例所要执行的任务清单，耗时以及所用到的板相关信息",response = InstanceTaskInfo.class)
    public ApiResponseInterface<InstanceTaskInfo> info(@PathVariable("instanceId")Long instanceId) {
        return ApiSuccessResponse.of(instanceTaskDTOService.info2(instanceId));
    }

    @GetMapping("/instanceTaskDynamic")
    @ApiOperation(value = "查询实例任务",notes = "获取正在运行中或者等待的任务信息(列表)",response = InstanceTaskDTO.class)
    public ApiResponseInterface< List<InstanceTaskDTO>> instanceTaskDynamic() {
        return ApiSuccessResponse.of(instanceTaskDTOService.instanceTaskDynamicList());
    }
    /**
     *
     * @return 实例信息
     */
    @GetMapping("/instanceTaskDynamicByRunning")
    @ApiOperation(value = "查询实例任务动态",notes = "获取实例任务动态，包含任务清单",response = InstanceTaskInfo.class)
    public ApiResponseInterface<List<InstanceTaskInfo>> instanceTaskDynamicByRunning() {
        return ApiSuccessResponse.of(instanceTaskDTOService.instanceTaskDynamicByRunning());
    }

    /**
     * 获取正在运行中的甘特图
     * @return 甘特图数据
     */
    @GetMapping("/getRunningInstanceGantt")
    @ApiOperation(value = "甘特图",notes = "获取正在运行中的实例任务，并组装成甘特图所需结构数据",response = GanttChart.class)
    public ApiResponseInterface<GanttChart> ganttByRunning() {
        return ApiSuccessResponse.of(instanceTaskDTOService.ganttByRunning());
    }
//    InstanceTaskCountDTO


    @GetMapping("/countInstanceTasks")
    @ApiOperation(value = "统计实例任务",notes = "统计正在运行中的实例任务信息,对总数、完成数、运行数的统计",response = InstanceTaskCountDTO.class)
    public ApiResponseInterface<InstanceTaskCountDTO> count() {
        Optional<InstanceTaskCountDTO> countOptional = instanceTaskDTOService.count();
        return countOptional.map(ApiSuccessResponse::of).orElseGet(ApiSuccessResponse::of);
    }
}
