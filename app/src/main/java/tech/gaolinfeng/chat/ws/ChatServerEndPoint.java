package tech.gaolinfeng.chat.ws;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.gaolinfeng.base.jsoncodec.JsonDecoder;
import tech.gaolinfeng.base.jsoncodec.JsonEncoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by gaolf on 16/10/21.
 */
@ServerEndpoint(value = "/chat", decoders = {JsonDecoder.class}, encoders = JsonEncoder.class)
public class ChatServerEndPoint {

    private static Logger logger = LoggerFactory.getLogger(ChatServerEndPoint.class);

    public ChatServerEndPoint() {
        // needed by @ServerEndPoint
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        logger.debug("onOpen");
    }

    @OnMessage
    public void handleMessage(JsonNode root, Session session) {
        logger.debug("onMessage, message: " + root);
    }

    @OnClose
    public void handleClose(Session session, CloseReason closeReason) {
        logger.debug("onClose");
    }

    @OnError
    public void onError() {
        logger.debug("onError");
    }


}
