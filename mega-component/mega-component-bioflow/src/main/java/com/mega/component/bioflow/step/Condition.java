package com.mega.component.bioflow.step;

import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:41
 */
public interface Condition {
    boolean evaluate();
}
