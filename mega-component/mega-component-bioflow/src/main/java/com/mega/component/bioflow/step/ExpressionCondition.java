package com.mega.component.bioflow.step;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 17:29
 */
public class ExpressionCondition implements Condition {
    private final String expression;

    public ExpressionCondition(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean evaluate() {
        // 在这里实现表达式求值逻辑，返回条件是否满足
        // ...
        return true; // 示例结果
    }
}
