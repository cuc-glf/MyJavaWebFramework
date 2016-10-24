package tech.gaolinfeng.chat.service;

/**
 * Created by gaolf on 16/10/24.
 * 纪录Server发送给客户端的消息ID及消息是否被成功接收, 客户端收到该类消息时, 必须给Server发送确认消息已收到的反馈
 */
public interface IServerMessageIdService {
    long generateId();
}
