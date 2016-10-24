package tech.gaolinfeng.base.jsoncodec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.ContextLoader;
import tech.gaolinfeng.chat.controller.TypedMessageResponse;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by gaolf on 16/10/21.
 * JSR 356 Websocket使用的接口, 用于将Message内容解析为Json
 */
public class JsonEncoder implements Encoder.Text<TypedMessageResponse> {

    private ObjectMapper objectMapper;

    @Override
    public String encode(TypedMessageResponse object) throws EncodeException {
        String result;
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new EncodeException(object, "JsonEncoder错误, Jackson无法json序列化对象");
        }
        return result;
    }

    @Override
    public void init(EndpointConfig config) {
        objectMapper = ContextLoader.getCurrentWebApplicationContext().getBean(ObjectMapper.class);
    }

    @Override
    public void destroy() {

    }
}
