package tech.gaolinfeng.chat.controller.ws;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.gaolinfeng.base.entity.User;
import tech.gaolinfeng.chat.annotation.MessageHandler;
import tech.gaolinfeng.chat.service.IClientMessageIdService;
import tech.gaolinfeng.chat.util.WebSocketUtil;
import tech.gaolinfeng.chat.ws.ISessionManager;

import javax.annotation.Resource;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaolf on 16/10/22.
 * 使用这个类作为WebSocket消息路由, 实际的消息处理由IMessageHandler接口负责
 */
@Component
public class ChatMessageController implements IChatMessageController {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private IClientMessageIdService clientMessageIdService;

    @Resource
    private ISessionManager sessionManager;

    private final Map<String, IMessageHandler> handlerMap = new HashMap<>();

    public ChatMessageController() {
    }


    /**
     * 注册MessageHandler的同时检查是否有路由冲突
     */
    private void registerMessageHandler(TypedMessageHandler typedMessageHandler) {
        String messageType = typedMessageHandler.getMessageType();
        if (handlerMap.containsKey(messageType)) {
            throw new RuntimeException("message handler for messageType: " + messageType +
                    " already exist!");
        } else {
            handlerMap.put(messageType, typedMessageHandler);
        }
    }

    @Override
    public void handleMessage(Session session, JsonNode root) throws IOException, EncodeException {

        lazyInit();

        Subject subject = WebSocketUtil.getSubjectAndTouch(session);
        User user = (User) subject.getPrincipal();
        String type = root.get("messageType").asText();
        // 检查消息是否具有ID, 如果有就说明该消息不可以重复处理, 应当纪录在ClientID表中, 对于已经存在的消息直接返回已经成功处理
        if (root.has("messageId")) {
            int messageId = root.get("messageId").asInt();
            if (messageId > 0) {
                boolean idHandled = clientMessageIdService.checkIdExist(messageId, user.getId());
                if (idHandled) {
                    sendObject(session, new MessageReceivedResponse(messageId));
                    return;
                } else {
                    // 新消息, 纪录, 处理并且返回
                    // 消息纪录和处理必须处于同一个事务中!
                    TypedMessageResponse response = handleMessageTransactional(session, root, subject, type, messageId);
                    if (response != null) {
                        if (!(response instanceof MessageReceivedResponse)) {
                            throw new RuntimeException("消息: " + root + "包含messageId, 需要返回MessageReceivedResponse对象");
                        }
                        sendObject(session, response);
                        return;
                    } else {
                        sendObject(session, new MessageReceivedResponse(messageId));
                        return;
                    }
                }
            }
        }
        TypedMessageResponse response = handleMessage(session, root, subject, type);
        if (response != null) {
            sendObject(session, response);
        }
    }

    private void sendObject(Session session, TypedMessageResponse response) throws IOException, EncodeException {
        sessionManager.send(response, s -> s == session);
    }

    private void lazyInit() {

        synchronized (handlerMap) {
            if (handlerMap.isEmpty()) {
                ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
                provider.addIncludeFilter(new AnnotationTypeFilter(MessageHandler.class));
                for (BeanDefinition beanDefinition : provider.findCandidateComponents(getClass().getPackage().getName())) {
                    String className = beanDefinition.getBeanClassName();
                    TypedMessageHandler messageHandler = null;
                    try {
                        messageHandler = (TypedMessageHandler) applicationContext.getBean(Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        throw new RuntimeException("fail looking for class: " + className);
                    }
                    String handleMessageType = messageHandler.getClass().getAnnotation(MessageHandler.class).value();
                    messageHandler.setMessageType(handleMessageType);
                    registerMessageHandler(messageHandler);
                }
            }
        }

    }

    @Transactional
    private TypedMessageResponse handleMessageTransactional(Session session, JsonNode root, Subject subject, String type, int messageId) throws IOException, EncodeException {
        clientMessageIdService.addId(messageId, ((User) subject.getPrincipal()).getId());
        return handleMessage(session, root, subject, type);
    }

    private TypedMessageResponse handleMessage(Session session, JsonNode root, Subject subject, String type) {
        if (handlerMap.containsKey(type)) {
            return handlerMap.get(type).handleMessage(session, root, subject);
        } else {
            throw new RuntimeException("message type not exist: " + type);
        }
    }

}
