package tech.gaolinfeng.chat.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.base.exceptions.ServiceRuntimeException;
import tech.gaolinfeng.base.util.CollectionUtil;
import tech.gaolinfeng.chat.entity.ClientMessageId;

import javax.annotation.Resource;

/**
 * Created by gaolf on 16/10/24.
 */
@Service
public class ClientMessageIdService implements IClientMessageIdService {

    @Resource
    private SqlSession session;

    @Transactional
    @Override
    public boolean checkIdExist(int id, int userId) {
        ClientMessageId clientMessageId = session.selectOne(
                "entity.chat.ClientMessageId.selectOne",
                CollectionUtil.asMap("id", id, "userId", userId));
        return clientMessageId != null;
    }

    @Transactional
    @Override
    public void addId(int id, int userId) {
        int affectedRows = session.insert(
                "entity.chat.ClientMessageId.insert",
                CollectionUtil.asMap("id", id, "userId", userId));
        if (affectedRows != 1) {
            throw new ServiceRuntimeException("ClientMessageId插入失败, id: " + id + ", userId: " + userId);
        }
    }

    @Transactional
    @Override
    public int getCurrentMessageId(int userId) {
        ClientMessageId clientMessageId = session.selectOne(
                "entity.chat.ClientMessageId.selectCurrentMessageId",
                CollectionUtil.asMap("userId", userId));
        if (clientMessageId == null) {
            return 1;
        }
        return clientMessageId.getId();
    }
}
