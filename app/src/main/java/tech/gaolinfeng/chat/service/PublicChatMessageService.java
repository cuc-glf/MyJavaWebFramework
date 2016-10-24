package tech.gaolinfeng.chat.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.chat.entity.PublicChatMessage;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/10/22.
 */
@Service
public class PublicChatMessageService implements IPublicChatMessageService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SqlSession session;

    @Transactional
    @Override
    public void addMessage(PublicChatMessage message) {
        session.insert("entity.chat.PublicChatMessage.insert", message);
        logger.info("message saved: " + message);
    }
}
