package tech.gaolinfeng.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Created by gaolf on 16/9/21.
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompOverWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").addInterceptors(loginInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        ThreadPoolTaskScheduler pingScheduler = new ThreadPoolTaskScheduler();
        pingScheduler.initialize();
        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{20000, 0}).setTaskScheduler(pingScheduler);
    }

    /**
     * 发送到这个Channel的回调, 会在收到客户端发来的消息时被调用
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                // 接收客户端的消息后, 更新session上次生效的时间, 避免session过期
                Subject subject = (Subject) StompHeaderAccessor.wrap(message).getSessionAttributes().get("subject");
                try {
                    subject.getSession().touch();
                } catch (InvalidSessionException e) {
                    e.printStackTrace();
                }

                System.out.println("preSend");
                return super.preSend(message, channel);
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                System.out.println("postSend");
                super.postSend(message, channel, sent);
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                System.out.println("afterSendCompletion");
                super.afterSendCompletion(message, channel, sent, ex);
            }

            @Override
            public boolean preReceive(MessageChannel channel) {
                System.out.println("preReceive");
                return super.preReceive(channel);
            }

            @Override
            public Message<?> postReceive(Message<?> message, MessageChannel channel) {
                System.out.println("postReceive");
                return super.postReceive(message, channel);
            }

            @Override
            public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
                System.out.println("afterReceiveCompletion");
                super.afterReceiveCompletion(message, channel, ex);
            }
        });
    }

    /**
     * 该Channel将消息发送出去的回调, 会在给客户端发消息时被调用
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                return super.preSend(message, channel);
            }
        });
    }

    private HandshakeInterceptor loginInterceptor = new HandshakeInterceptor() {
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            Subject subject = SecurityUtils.getSubject();

            if (!subject.isAuthenticated()) {
                // 未登录用户不允许链接
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }

            attributes.put("subject", subject);

            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        }

    };
}
