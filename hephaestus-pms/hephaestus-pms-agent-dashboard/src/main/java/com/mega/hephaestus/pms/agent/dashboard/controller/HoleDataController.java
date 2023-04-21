package com.mega.hephaestus.pms.agent.dashboard.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiPageListResponse;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HoleDataDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.HoleDTOService;
import com.mega.hephaestus.pms.data.mysql.entity.PlateHoleDataHistoryEntity;
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
@RequestMapping("/holeData")
@RequiredArgsConstructor
@ApiSupport(order = 1)
public class HoleDataController {

    private final HoleDTOService holeDTOService;
    /**
     * 设备类型列表
     */
    @GetMapping("/getByPlateId")
    @ApiOperation(value = "获取孔位数据列表", notes = "根据id查询获取孔位数据列表", response = HoleDataDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<HoleDataDTO> list(@RequestParam("plateId") long plateId) {
        return ApiPageListResponse.of( holeDTOService.holeDataByPlateId(plateId));
    }

    @GetMapping("/history/items")
    @ApiOperation(value = "获取孔位历史数据列表", notes = "获取孔位历史数据列表", response = HoleDataDTO.class)
    @ApiOperationSupport(order = 1)
    public ApiResponseInterface<List<PlateHoleDataHistoryEntity>> holeDataHistoryList(@RequestParam("holeDataId") long holeDataId) {
        return ApiPageListResponse.of( holeDTOService.holeDataHistoryListByHoleDataId(holeDataId));
    }

}
