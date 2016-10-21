package tech.gaolinfeng.base.jsoncodec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.ContextLoader;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by gaolf on 16/10/21.
 */
public class JsonEncoder<T> implements Encoder.Text<T> {

    private ObjectMapper objectMapper;

    @Override
    public String encode(T object) throws EncodeException {
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
