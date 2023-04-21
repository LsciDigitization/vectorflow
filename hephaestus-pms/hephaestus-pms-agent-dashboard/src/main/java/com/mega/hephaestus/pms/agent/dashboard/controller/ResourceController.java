package com.mega.hephaestus.pms.agent.dashboard.controller;



import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.component.api.response.ApiResponseInterface;
import com.mega.component.api.response.ApiSuccessResponse;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.ResourceDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.ResourceDTOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HephaestusExperimentGroupController
 *
 * @author 胡贤明
 */
@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
@Api(tags = "资源(Resource)")
@ApiSupport(order = 30)
public class ResourceController {


  private final ResourceDTOService resourceDTOService;

    @GetMapping(value = "/resourceByRunning")
    @ApiOperation(value = "查询资源",notes = "查询正在运行中的资源信息",response = ResourceDTO.class)
    public ApiResponseInterface<List<ResourceDTO>> resourceColorByRunning() {
        return ApiSuccessResponse.of(resourceDTOService.resourceColorByRunning());
    }

  @GetMapping(value = "/listStorageDevice")
  @ApiOperation(value = "获取存储类设备",notes = "获取存储类设备",response = ResourceDTO.class)
  public ApiResponseInterface<List<ResourceDTO>> listStorageDevice() {
    return ApiSuccessResponse.of(resourceDTOService.listStorageDevice());
  }

}
