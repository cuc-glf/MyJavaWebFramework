package tech.gaolinfeng.chat.entity;

import java.util.Date;

/**
 * Created by gaolf on 16/10/21.
 * 公共聊天室消息纪录
 */
public class PublicChatMessage {
    private int id;
    private int sendUserId;
    private Date sendTime;
    private String message;

    public PublicChatMessage() {
        // required by mybatis
    }

    public PublicChatMessage(int sendUserId, Date sendTime, String message) {
        this.sendUserId = sendUserId;
        this.sendTime = sendTime;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
