package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.hephaestus.pms.agent.dashboard.config.DashboardProjectProperties;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardDTOService {

    private final DashboardProjectProperties dashboardProjectProperties;




    public DashboardDTO dashboard() {

        DashboardDTO dashBoardDTO = new DashboardDTO();
        dashBoardDTO.setProjectName(dashboardProjectProperties.getProjectName());
        dashBoardDTO.setProjectStatus(dashboardProjectProperties.getProjectStatus());
        dashBoardDTO.setProjectDescription(dashboardProjectProperties.getProjectDescription());

        List<DashboardDTO.Link> collect = dashboardProjectProperties.getLinks().stream().map(v -> {
            DashboardDTO.Link link = new DashboardDTO.Link();
            link.setUrl(v.getUrl());
            link.setLinkName(v.getLinkName());
            return link;
        }).collect(Collectors.toList());
        dashBoardDTO.setLinks(collect);
        return dashBoardDTO;
    }


}
