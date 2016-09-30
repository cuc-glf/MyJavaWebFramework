package tech.gaolinfeng.config;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Created by gaolf on 16/9/22.
 * 用来初始化WebDataBinder, 添加自定义的converter
 */
@ControllerAdvice("tech.gaolinfeng.controller")
public class ControllerAdviceConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

}
