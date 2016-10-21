package tech.gaolinfeng.base.controller;

/**
 * Created by gaolf on 16/9/28.
 */
public class CommonResponse {

    /**
     * 请求正常返回
     */
    public static int CODE_OK = 0;
    /**
     * 请求处理过程中服务器抛异常
     */
    public static int SERVER_INTERNAL_ERROR = 1;
    /**
     * 请求的资源找不到
     */
    public static int RESOURCE_NOT_FOUND = 2;
    /**
     * 请求参数错误
     */
    public static final int PARAM_NOT_ALLOWED = 3;
    /**
     * 需要用户登录才可访问
     */
    public static final int TOKEN_ERROR = 4;
    /**
     * 不允许请求该资源
     */
    public static final int REQUEST_NOT_ALLOWED = 5;

    public int code;
    public String info;

    public CommonResponse(String info) {
        this(CODE_OK, info);
    }

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
