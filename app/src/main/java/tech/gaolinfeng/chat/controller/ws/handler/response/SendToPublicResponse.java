package tech.gaolinfeng.chat.controller.ws.handler.response;

import tech.gaolinfeng.chat.controller.ws.ClientMessageType;
import tech.gaolinfeng.chat.controller.ws.TypedMessageResponse;

import java.util.Date;

/**
 * Created by gaolf on 16/10/25.
 */

public class SendToPublicResponse extends TypedMessageResponse {
    public String content;      // 发言内容
    public Date time;           // 发言时间
    public String senderName;   // 发言用户名

    public SendToPublicResponse(String content, Date time, String senderName) {
        super(ClientMessageType.PublicMessage);

        this.content = content;
        this.time = time;
        this.senderName = senderName;
    }
}
