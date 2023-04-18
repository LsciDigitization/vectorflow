package com.mega.component.bioflow.step;

import lombok.Data;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:41
 */
@Data
public class Hook {
    private String command;
    private List<String> parameters;
}
