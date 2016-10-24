package tech.gaolinfeng.chat.controller.ws;

/**
 * Created by gaolf on 16/10/24.
 */
public class MessageReceivedResponse extends TypedMessageResponse {

    public long id;

    public MessageReceivedResponse(long id) {
        super(ClientMessageType.MessageReceived);
        this.id = id;
    }
}
