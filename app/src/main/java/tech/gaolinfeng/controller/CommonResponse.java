package tech.gaolinfeng.controller;

/**
 * Created by gaolf on 16/9/28.
 */
public class CommonResponse {

    public static int CODE_OK = 0;
    public static int SERVER_INTERNAL_ERROR = 1;
    public static int RESOURCE_NOT_FOUND = 2;
    public static final int PARAM_NOT_ALLOWED = 3;
    public static final int TOKEN_ERROR = 4;

    public int code;
    public String info;

    public CommonResponse(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public static CommonResponse createInternalErrorException() {
        return new CommonResponse(SERVER_INTERNAL_ERROR, "server internal error");
    }

    public static CommonResponse createResourceNotFoundResponse() {
        return new CommonResponse(RESOURCE_NOT_FOUND, "resource not found");
    }

}
