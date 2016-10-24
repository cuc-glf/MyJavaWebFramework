package tech.gaolinfeng.chat.controller.ws;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.shiro.subject.Subject;

import javax.websocket.Session;

/**
 * Created by gaolf on 16/10/22.
 */
public interface IMessageHandler {

    TypedMessageResponse handleMessage(Session session, JsonNode root, Subject subject);

}
