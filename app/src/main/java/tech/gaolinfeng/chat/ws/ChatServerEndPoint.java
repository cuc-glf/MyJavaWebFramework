package tech.gaolinfeng.chat.ws;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.gaolinfeng.base.jsoncodec.JsonDecoder;
import tech.gaolinfeng.base.jsoncodec.JsonEncoder;
import tech.gaolinfeng.chat.controller.IChatMessageController;
import tech.gaolinfeng.chat.controller.TypedMessageResponse;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by gaolf on 16/10/21.
 */
@ServerEndpoint(value = "/chat", decoders = {JsonDecoder.class}, encoders = {JsonEncoder.class}, configurator = ShiroSpringConfigurator.class)
public class ChatServerEndPoint {

    private static Logger logger = LoggerFactory.getLogger(ChatServerEndPoint.class);

    @Resource
    private IChatMessageController chatMessageController;

    @Resource
    private ISessionManager sessionManager;

    public ChatServerEndPoint() {
        // needed by @ServerEndPoint
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        logger.info("onOpen");
        sessionManager.onSessionOpened(session);
    }

    @OnMessage
    public TypedMessageResponse handleMessage(JsonNode root, Session session) throws IOException, EncodeException {
        logger.info("onMessage, message: " + root);
        TypedMessageResponse response = chatMessageController.handleMessage(session, root);
        return response;
    }

    @OnClose
    public void handleClose(Session session, CloseReason closeReason) {
        logger.info("onClose");
        sessionManager.onSessionClosed(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("onError");
        throwable.printStackTrace();
    }



}
