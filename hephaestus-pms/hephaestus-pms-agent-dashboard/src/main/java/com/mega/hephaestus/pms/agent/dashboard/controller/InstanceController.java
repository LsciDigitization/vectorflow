package com.mega.hephaestus.pms.agent.dashboard.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.mega.hephaestus.pms.agent.dashboard.domain.service.InstanceDTOService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * InstanceController
 *
 * @author 胡贤明
 */
@RestController
@RequestMapping("/instance")
@RequiredArgsConstructor
@Api(tags = "实例(Instance)")
@ApiSupport(order = 60)
public class InstanceController {


    private final InstanceDTOService instanceDTOService;

}
