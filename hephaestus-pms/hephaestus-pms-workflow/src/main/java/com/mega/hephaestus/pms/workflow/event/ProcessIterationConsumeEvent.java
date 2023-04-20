package com.mega.hephaestus.pms.workflow.event;

import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.component.bioflow.task.ProjectId;
import org.springframework.context.ApplicationEvent;

/**
 * 通量消费事件
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 17:17
 */
public class ProcessIterationConsumeEvent extends ApplicationEvent {

    private final ProjectId projectId;
    private final ProcessRecordId processRecordId;
    private final Integer iterationNo;

    public ProcessIterationConsumeEvent(Object source, ProjectId projectId, ProcessRecordId processRecordId, Integer iterationNo) {
        super(source);
        this.projectId = projectId;
        this.processRecordId = processRecordId;
        this.iterationNo = iterationNo;
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

    @Override
    public String toString() {
        return "ProcessIterationConsumeEvent{" +
                "projectId=" + projectId +
                ", processRecordId=" + processRecordId +
                ", iterationNo=" + iterationNo +
                "} " + super.toString();
    }
}
