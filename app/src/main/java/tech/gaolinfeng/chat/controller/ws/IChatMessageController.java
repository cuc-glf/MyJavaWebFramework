package tech.gaolinfeng.chat.controller.ws;

import com.fasterxml.jackson.databind.JsonNode;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by gaolf on 16/10/22.
 */
public interface IChatMessageController {
    void handleMessage(Session session, JsonNode root) throws IOException, EncodeException;
}
