package tech.gaolinfeng.chat.service;

import tech.gaolinfeng.chat.entity.PublicChatMessage;

/**
 * Created by gaolf on 16/10/22.
 * 公共聊天室消息接口
 */
public interface IPublicChatMessageService {
    /**
     * 公共聊天室新消息持久化
     */
    void addMessage(PublicChatMessage message);
}
