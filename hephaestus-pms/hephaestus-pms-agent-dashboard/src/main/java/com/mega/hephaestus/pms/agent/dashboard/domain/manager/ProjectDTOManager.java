package com.mega.hephaestus.pms.agent.dashboard.domain.manager;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.ProcessModel;
import com.mega.hephaestus.pms.data.model.entity.ProcessEntity;
import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;
import com.mega.hephaestus.pms.data.model.enums.ExperimentGroupStatusEnum;
import com.mega.hephaestus.pms.data.model.service.IProcessService;
import com.mega.hephaestus.pms.data.model.service.IProjectService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectDTOManager {

    // 记录service
    private final IProcessRecordService historyService;

    // 流程service
    private final IProcessService processService;
    // 项目service
    private final IProjectService projectService;

    //
    private final ExperimentProperties experimentProperties;

    /**
     * 获取当前的项目
     */
    public Optional<ProcessModel> getCurrentProcess() {

        // 有运行的就拿到运行 、 没有运行就拿到最后一次完成的
        Optional<ProcessRecordEntity> currentProcessOptional = historyService.getLast();
        if (currentProcessOptional.isPresent()) {
            ProcessRecordEntity currentProject = currentProcessOptional.get();
            // 流程id
            Long processId = currentProject.getProcessId();
            // 流程
            ProcessEntity process = processService.getById(processId);
            // 项目
            ProjectEntity project = projectService.getById(process.getProjectId());

            ProcessModel projectModel = projectToModel(project);
            Integer groupStatus = currentProject.getProcessStatus();

            projectModel.setStatusCode(String.valueOf(groupStatus));
            if (Objects.nonNull(groupStatus)) {
                projectModel.setStatus(ExperimentGroupStatusEnum.toEnum(groupStatus).getName());
            } else {
                projectModel.setStatus("running");
            }
            projectModel.setId(processId);
            return Optional.of(projectModel);
        }

        return Optional.empty();
    }

    private ProcessModel projectToModel(ProjectEntity project) {

        ProcessModel model = new ProcessModel();
        model.setProjectId(project.getId());
        model.setName(project.getResourceGroupName());
        model.setDescription(model.getDescription());
        model.setType(project.getExperimentType());
        return model;
    }
}
