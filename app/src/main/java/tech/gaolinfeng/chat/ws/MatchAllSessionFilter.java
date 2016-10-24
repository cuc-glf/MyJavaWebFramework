package tech.gaolinfeng.chat.ws;

import javax.websocket.Session;

/**
 * Created by gaolf on 16/10/22.
 */
public class MatchAllSessionFilter implements ISessionFilter {

    @Override
    public boolean filter(Session session) {
        return true;
    }
}
