package tech.gaolinfeng.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gaolf on 16/10/24.
 */
@Service
public class ServerMessageIdService implements IServerMessageIdService {

    @Transactional
    @Override
    public long generateId() {
        return 0;
    }
}
