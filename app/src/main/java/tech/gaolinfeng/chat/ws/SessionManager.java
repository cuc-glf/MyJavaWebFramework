package tech.gaolinfeng.chat.ws;

import org.springframework.stereotype.Component;
import tech.gaolinfeng.chat.controller.TypedMessageResponse;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gaolf on 16/10/22.
 */
@Component
public class SessionManager implements ISessionManager {
    private final List<Session> connectedSessions = new LinkedList<>();

    public SessionManager() {

    }

    @Override
    public void onSessionOpened(Session session) {
        synchronized (connectedSessions) {
            connectedSessions.add(session);
        }
    }

    @Override
    public void onSessionClosed(Session session) {
        synchronized (connectedSessions) {
            connectedSessions.remove(session);
        }
    }

    @Override
    public void send(TypedMessageResponse response, ISessionFilter filter) throws IOException, EncodeException {
        List<Session> sessions = new ArrayList<>(connectedSessions.size());
        synchronized (connectedSessions) {
            sessions.addAll(connectedSessions);
        }
        for (Session session : sessions) {
            if (filter.filter(session)) {
                session.getBasicRemote().sendObject(response);
            }
        }
    }


}
