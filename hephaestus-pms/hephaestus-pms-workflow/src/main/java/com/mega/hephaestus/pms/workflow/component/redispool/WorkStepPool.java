package com.mega.hephaestus.pms.workflow.component.redispool;

import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.component.sortedset.AbstractSortedSet;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import org.springframework.data.redis.core.RedisTemplate;

public class WorkStepPool extends AbstractSortedSet<Step> {

    private final String groupKey;

    private final PlateType plateType;

    // redis key前缀
    private final static String redisKeyPrefix = "WorkStep:";


    public WorkStepPool(String groupKey, PlateType plateType, RedisTemplate<String, Step> redisTemplate) {
        super(redisTemplate);
        this.redisKey = String.format("%s%s:%s:", redisKeyPrefix, groupKey, plateType);

        this.groupKey = groupKey;
        this.plateType = plateType;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public PlateType getPlateType() {
        return plateType;
    }

    @Override
    public Step[] toArray() {
        return toList().toArray(Step[]::new);
    }

}
