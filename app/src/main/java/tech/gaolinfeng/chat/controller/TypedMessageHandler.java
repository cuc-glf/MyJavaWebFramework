package tech.gaolinfeng.chat.controller;

/**
 * Created by gaolf on 16/10/22.
 */
public abstract class TypedMessageHandler implements IMessageHandler {

    protected String messageType;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}