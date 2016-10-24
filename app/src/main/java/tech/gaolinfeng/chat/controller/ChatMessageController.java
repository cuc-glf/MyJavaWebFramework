package tech.gaolinfeng.chat.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import tech.gaolinfeng.chat.annotation.MessageHandler;

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
    public TypedMessageResponse handleMessage(Session session, JsonNode root) throws IOException, EncodeException {

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

        String type = root.get("messageType").asText();
        if (handlerMap.containsKey(type)) {
            return handlerMap.get(type).handleMessage(session, root);
        } else {
            throw new RuntimeException("message type not exist: " + type);
        }
    }

}
