package com.mega.hephaestus.pms.workflow.event;

import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 实验结束事件
 */
public class StartWorkflowBeforeEvent extends ApplicationEvent {

    private final InstanceEntity instanceEntity;

    private final HephaestusResourceStorage hephaestusStorage;


    public StartWorkflowBeforeEvent(InstanceEntity instanceEntity, HephaestusResourceStorage hephaestusStorage) {
        super(instanceEntity.getId());
        this.instanceEntity = instanceEntity;
        this.hephaestusStorage = hephaestusStorage;
    }

    public InstanceEntity getHephaestusInstance() {
        return instanceEntity;
    }

    public HephaestusResourceStorage getHephaestusStorage() {
        return hephaestusStorage;
    }

    @Override
    public String toString() {
        return "StartWorkflowEvent{" +
                "instanceEntity=" + instanceEntity +
                ", hephaestusStorage=" + hephaestusStorage +
                "} " + super.toString();
    }
}
