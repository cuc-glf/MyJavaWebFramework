package tech.gaolinfeng.chat.ws;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by gaolf on 16/10/22.
 */
public class ShiroSpringConfigurator extends SpringConfigurator {

    public static final String KEY_SUBJECT = "KEY_SUBJECT";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        logger.info("modifyHandshake");

        boolean authSucceed = shiroAuth(sec);
        if (!authSucceed) {
            authSucceed = urlAuth(sec, request);
        }
        if (!authSucceed) {
            RuntimeException e = new UnauthorizedException();
            logger.info("用户未登录, 放弃WebSocket链接");
            throw e;
        }

    }

    private boolean shiroAuth(ServerEndpointConfig sec) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            sec.getUserProperties().put(KEY_SUBJECT, subject);
            return true;
        } else {
            return false;
        }
    }

    private boolean urlAuth(ServerEndpointConfig sec, HandshakeRequest request) {
        String query = request.getRequestURI().getQuery();
        Subject subject = new Subject.Builder().sessionId(query).buildSubject();
        if (subject.isAuthenticated()) {
            sec.getUserProperties().put(KEY_SUBJECT, subject);
            return true;
        } else {
            return false;
        }
    }
}
