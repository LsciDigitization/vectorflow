package com.mega.hephaestus.pms.nuc.config.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class NucDeviceNoDebugCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return "false".equalsIgnoreCase(context.getEnvironment().getProperty("hephaestus.nuc.debug"));
    }
}
