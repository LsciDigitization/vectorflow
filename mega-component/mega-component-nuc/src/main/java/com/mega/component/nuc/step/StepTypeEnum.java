package com.mega.component.nuc.step;

import com.mega.component.nuc.message.EnumConst;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public enum StepTypeEnum implements StepType {

    NONE("none", "NONE", StepTypeEnum.STEP_NONE),

    STEP1("step1", "STEP1", StepTypeEnum.STEP_START),
    STEP2("step2", "STEP2", StepTypeEnum.STEP_START << 1),
    STEP3("step3", "STEP3", StepTypeEnum.STEP_START << 2),
    STEP4("step4", "STEP4", StepTypeEnum.STEP_START << 3),
    STEP5("step5", "STEP5", StepTypeEnum.STEP_START << 4),
    STEP6("step6", "STEP6", StepTypeEnum.STEP_START << 5),
    STEP7("step7", "STEP7", StepTypeEnum.STEP_START << 6),
    STEP8("step8", "STEP8", StepTypeEnum.STEP_START << 7),
    STEP9("step9", "STEP9", StepTypeEnum.STEP_START << 8),
    STEP10("step10", "STEP10", StepTypeEnum.STEP_START << 9),
    STEP11("step11", "STEP11",  StepTypeEnum.STEP_START << 10),
    STEP12("step12", "STEP12", StepTypeEnum.STEP_START << 11),
    STEP13("step13", "STEP13", StepTypeEnum.STEP_START << 12),
    STEP14("step14", "STEP14", StepTypeEnum.STEP_START << 13),
    STEP15("step15", "STEP15", StepTypeEnum.STEP_START << 14),
    STEP16("step16", "STEP16", StepTypeEnum.STEP_START << 15),
    STEP17("step17", "STEP17", StepTypeEnum.STEP_START << 16),
    STEP18("step18", "STEP18", StepTypeEnum.STEP_START << 17),
    STEP19("step19", "STEP19", StepTypeEnum.STEP_START << 18),
    STEP20("step20", "STEP20", StepTypeEnum.STEP_START << 19),
    STEP21("step21", "STEP21", StepTypeEnum.STEP_START << 20),
    STEP22("step22", "STEP22", StepTypeEnum.STEP_START << 21),
    STEP23("step23", "STEP23", StepTypeEnum.STEP_START << 22),
    STEP24("step24", "STEP24", StepTypeEnum.STEP_START << 23),
    STEP25("step25", "STEP25", StepTypeEnum.STEP_START << 24),
    STEP26("step26", "STEP26", StepTypeEnum.STEP_START << 25),
    STEP27("step27", "STEP27", StepTypeEnum.STEP_START << 26),
    STEP28("step28", "STEP28", StepTypeEnum.STEP_START << 27),
    STEP29("step29", "STEP29", StepTypeEnum.STEP_START << 28),
    STEP30("step30", "STEP30", StepTypeEnum.STEP_START << 29),
    STEP31("step31", "STEP31", StepTypeEnum.STEP_START << 30),

    ;

    private static final int STEP_NONE = 0;
    private static final int STEP_START = 1;

    private final String code;

    private final long value;

    private final String label;

    StepTypeEnum(String code, long value) {
        this.code = code;
        this.value = value;
        this.label = code;
    }

    StepTypeEnum(String code, String label, long value) {
        this.code = code;
        this.value = value;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return code;
    }

    public static StepTypeEnum toEnum(final String code) {
        for (StepTypeEnum versionEnum : StepTypeEnum.values()) {
            if (versionEnum.getCode().equals(code)) {
                return versionEnum;
            }
        }
        throw new IllegalArgumentException(String.format(EnumConst.NO_MATCHING_ENUMS_ERROR_MESSAGE, code));
    }

    public long getStepTotal() {
        int step1 = 1;
        String step2 = StringUtils.remove(this.code, "step");
        IntStream intStream = IntStream.rangeClosed(step1, Integer.parseInt(step2));
        return intStream
                .mapToLong(v -> {
                    String s1 = "step" + v;
                    StepTypeEnum stepType = StepTypeEnum.toEnum(s1);
                    return stepType.getValue();
                })
                .sum();
    }

    public long getStepTotal(StepTypeEnum type1) {
        String step1s = StringUtils.remove(type1.getCode(), "step");
        int step1 = Integer.parseInt(step1s);
        String step2 = StringUtils.remove(this.code, "step");
        IntStream intStream = IntStream.rangeClosed(step1, Integer.parseInt(step2));
        return intStream
                .mapToLong(v -> {
                    String s1 = "step" + v;
                    StepTypeEnum stepType = StepTypeEnum.toEnum(s1);
                    return stepType.getValue();
                })
                .sum();
    }

}
