package tech.gaolinfeng.chat.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by gaolf on 16/10/22.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MessageHandler {
    String value();
}
