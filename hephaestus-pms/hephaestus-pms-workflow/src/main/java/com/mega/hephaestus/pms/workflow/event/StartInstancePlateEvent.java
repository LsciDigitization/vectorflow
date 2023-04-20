package com.mega.hephaestus.pms.workflow.event;

import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.component.nuc.plate.PlateType;
import org.springframework.context.ApplicationEvent;

/**
 * 板子消费事件
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 17:44
 */
public class StartInstancePlateEvent extends ApplicationEvent {

    private final ProjectId projectId;
    private final ProcessRecordId processRecordId;
    private final Integer iterationNo;
    private final PlateType plateType;

    public StartInstancePlateEvent(Object source, ProjectId projectId, ProcessRecordId processRecordId, Integer iterationNo, PlateType plateType) {
        super(source);
        this.projectId = projectId;
        this.processRecordId = processRecordId;
        this.iterationNo = iterationNo;
        this.plateType = plateType;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public ProcessRecordId getProcessRecordId() {
        return processRecordId;
    }

    public Integer getIterationNo() {
        return iterationNo;
    }

    public PlateType getPlateType() {
        return plateType;
    }

    @Override
    public String toString() {
        return "StartInstancePlateEvent{" +
                "projectId=" + projectId +
                ", processRecordId=" + processRecordId +
                ", iterationNo=" + iterationNo +
                ", plateType=" + plateType +
                "} " + super.toString();
    }
}
