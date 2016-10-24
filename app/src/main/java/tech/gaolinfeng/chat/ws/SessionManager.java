package tech.gaolinfeng.chat.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.gaolinfeng.chat.controller.ws.TypedMessageResponse;

import javax.websocket.PongMessage;
import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by gaolf on 16/10/22.
 */
@Component
public class SessionManager implements ISessionManager {

    private final Object writeLock = new Object();

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final byte[] pingData = "ping".getBytes();

    private final Map<Session, SessionInfo> connectedSessions = new HashMap<>();
    // 开启三个用于发送ping的线程
    private ScheduledExecutorService pingSender = Executors.newSingleThreadScheduledExecutor();
//    // 开启十个用于给session发送消息的线程
//    private ExecutorService sessionSender = Executors.newFixedThreadPool(10);

    public SessionManager() {

    }

    @Override
    public void onSessionOpened(Session session) {
        // schedule ping task
        Future future = pingSender.scheduleAtFixedRate(
                new PingRunnable(session), PING_INTERVAL, PING_INTERVAL, TimeUnit.MILLISECONDS);
        synchronized (connectedSessions) {
            connectedSessions.put(session, new SessionInfo(future));
        }
    }

    @Override
    public void onSessionClosed(Session session) {
        // cancel ping task
        connectedSessions.get(session).pingTask.cancel(false);
        synchronized (connectedSessions) {
            connectedSessions.remove(session);
        }
    }

    @Override
    public void send(TypedMessageResponse response, ISessionFilter filter) {
        List<Session> sessions = new ArrayList<>(connectedSessions.size());
        synchronized (connectedSessions) {
            sessions.addAll(connectedSessions.keySet());
        }
        for (Session session : sessions) {
            if (filter.filter(session)) {
                if (session.isOpen()) {
                    synchronized (writeLock) {
                        session.getAsyncRemote().sendObject(response);
                    }
                }

            }
        }
    }

    @Override
    public void onPongMessage(Session session, PongMessage pongMessage) {
        logger.info("onPongMessage, session: " + session.getId());
        if (pongMessage.getApplicationData().compareTo(ByteBuffer.wrap(pingData)) == 0) {
            connectedSessions.get(session).lastPongTime = System.currentTimeMillis();
        }
    }

    private void close(Session session) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class PingRunnable implements Runnable {
        private Session session;

        public PingRunnable(Session session) {
            this.session = session;
        }

        @Override
        public void run() {
            long curr = System.currentTimeMillis();
            long lastPongTime = connectedSessions.get(session).lastPongTime;
            if (lastPongTime != 0 && curr - lastPongTime > PING_INTERVAL * 3) {
                logger.info("ping长时间(" + (curr - lastPongTime) + "ms)没有回应, 链接丢失, 关闭session: " + session);
                close(session);
            } else {
                try {
                    synchronized (writeLock) {
                        session.getAsyncRemote().sendPing(ByteBuffer.wrap(pingData));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info("ping发送失败, 关闭session: " + session);
                    close(session);
                }
            }
        }
    }

    private static class SessionInfo {
        public Future pingTask;
        public long lastPongTime = 0;

        public SessionInfo(Future pingTask) {
            this.pingTask = pingTask;
        }
    }

}
