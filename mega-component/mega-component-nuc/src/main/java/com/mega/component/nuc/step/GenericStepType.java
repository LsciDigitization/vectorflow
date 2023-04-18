package com.mega.component.nuc.step;

import com.mega.component.nuc.plate.GenericPlateType;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 11:28
 */
public class GenericStepType implements StepType {
    private final String code;
    private final String label;
    private final long value;

    public GenericStepType(String code, long value) {
        this(code, code, value);
    }

    public GenericStepType(String code, String label, long value) {
        this.code = code;
        this.label = label;
        this.value = value;
    }

    @Override
    public String name() {
        return code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericStepType that = (GenericStepType) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public static StepType toEnum(final String code) {
        StepTypeEnum stepTypeEnum = StepTypeEnum.toEnum(code);
        long value = stepTypeEnum.getValue();
        return new GenericStepType(code, value);
    }

    public static StepType toEnum(final String code, final long value) {
        return new GenericStepType(code, value);
    }

    public static StepType toEnum(final String code, final String label, final long value) {
        return new GenericStepType(code, label, value);
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

}
