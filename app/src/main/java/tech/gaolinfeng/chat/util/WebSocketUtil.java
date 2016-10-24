package tech.gaolinfeng.chat.util;

import org.apache.shiro.subject.Subject;
import tech.gaolinfeng.chat.ws.ShiroSpringConfigurator;

import javax.websocket.Session;
import java.util.Map;

/**
 * Created by gaolf on 16/10/22.
 */
public class WebSocketUtil {

    private WebSocketUtil() {
        // prevent instance creation
    }

    public static Subject getSubjectAndTouch(Session webSocketSession) {
        Map<String, Object> m = webSocketSession.getUserProperties();
        if (m.containsKey(ShiroSpringConfigurator.KEY_SUBJECT)) {
            Subject subject = (Subject) m.get(ShiroSpringConfigurator.KEY_SUBJECT);
            subject.getSession().touch();
            return subject;
        } else {
            throw new RuntimeException();
        }
    }

}
