package com.mega.hephaestus.pms.workflow.work.worktask;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MessageDomain<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String messageId;

    private T content;

    private Integer retryCount = 0;

    // 消息状态
    private MessageStatusEnum status = MessageStatusEnum.Default;
    // 消息主题
    private MessageTopicEnum topic = MessageTopicEnum.Default;

}
