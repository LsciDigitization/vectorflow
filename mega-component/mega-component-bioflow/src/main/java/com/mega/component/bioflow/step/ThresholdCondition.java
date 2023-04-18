package com.mega.component.bioflow.step;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 17:30
 */
public class ThresholdCondition implements Condition {

    private final double threshold;

    public ThresholdCondition(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean evaluate() {
        // 在这里实现阈值判断逻辑，返回条件是否满足
        // ...
        return true; // 示例结果
    }

}
