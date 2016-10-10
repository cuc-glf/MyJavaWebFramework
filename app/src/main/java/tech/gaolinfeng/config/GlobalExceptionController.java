package tech.gaolinfeng.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.gaolinfeng.controller.CommonResponse;

/**
 * Created by gaolf on 16/9/28.
 * 对所有Controller生效
 * 当任何Controller抛出异常时, 都打印异常信息, 并返回服务器内部错误.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionController {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody CommonResponse handleError(Throwable e) {
        e.printStackTrace();
        return CommonResponse.createInternalErrorException();
    }
}
