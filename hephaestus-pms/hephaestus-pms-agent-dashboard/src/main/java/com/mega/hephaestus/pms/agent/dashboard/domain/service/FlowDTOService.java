package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.component.bioflow.flow.Edge;
import com.mega.component.bioflow.flow.EdgeGroup;
import com.mega.component.bioflow.flow.FlatNode;
import com.mega.component.bioflow.flow.GraphNodeGroup;
import com.mega.component.bioflow.task.ProcessId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProjectDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.ProcessModel;
import com.mega.hephaestus.pms.workflow.manager.GraphManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowDTOService {
    private final GraphManager graphManager;

    private final ProjectDTOManager projectManager;

    public List<FlatNode> getFlatNode() {
        Optional<ProcessModel> currentProjectOptional = projectManager.getCurrentProcess();

        if (currentProjectOptional.isPresent()) {
            ProcessModel processModel = currentProjectOptional.get();
            ProjectId projectId = new ProjectId(processModel.getProjectId());
            ProcessId processId = new ProcessId(processModel.getId());
            GraphNodeGroup graphNodeGroup = graphManager.getGraphNodeGroup(projectId, processId);
            if(Objects.nonNull(graphNodeGroup)){
                return graphNodeGroup.toFlatNodes();
            }
           return List.of();
        }


        return List.of();
    }

    public List<Edge> getEdges() {
        Optional<ProcessModel> currentProjectOptional = projectManager.getCurrentProcess();

        if (currentProjectOptional.isPresent()) {
            ProcessModel processModel = currentProjectOptional.get();
            ProjectId projectId = new ProjectId(processModel.getProjectId());
            ProcessId processId = new ProcessId(processModel.getId());
           ;
            EdgeGroup edgeGroup = graphManager.getEdgeGroup(projectId,processId);
            if (Objects.nonNull(edgeGroup)) {
                return edgeGroup.getEdges();
            }
            return List.of();
        }
        return List.of();
    }
}
