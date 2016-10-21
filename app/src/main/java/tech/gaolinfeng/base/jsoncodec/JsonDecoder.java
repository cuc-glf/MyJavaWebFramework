package tech.gaolinfeng.base.jsoncodec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.ContextLoader;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Created by gaolf on 16/10/21.
 */
public class JsonDecoder implements Decoder.Text<JsonNode> {

    private ObjectMapper objectMapper;

    @Override
    public JsonNode decode(String s) throws DecodeException {
        JsonNode root;
        try {
            root = objectMapper.readTree(s);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DecodeException(s, "无法解析消息, 不是合法的Json对象");
        }
        return root;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
        objectMapper = ContextLoader.getCurrentWebApplicationContext().getBean(ObjectMapper.class);
    }

    @Override
    public void destroy() {

    }
}
