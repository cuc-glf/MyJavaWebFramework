package tech.gaolinfeng.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gaolf on 16/9/28.
 */
@RestController
public class CommonErrorController {

    @RequestMapping("/notFound")
    public CommonResponse handleResourceNotFound() {
        return CommonResponse.createResourceNotFoundResponse();
    }

}
