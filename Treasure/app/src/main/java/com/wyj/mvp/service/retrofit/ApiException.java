package com.wyj.mvp.service.retrofit;

/**
 * @author wangyujie
 * @date 2018/9/19.15:49
 * @describe 添加描述
 */
public class ApiException extends RuntimeException {

    private static String message;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }


    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        switch (code) {
            case 100:
                message = "该用户不存在";
                break;
            case 101:
                message = "密码错误";
                break;
            case 1000:
                message = "取消dialog";
                break;
            default:
                message = "未知错误";
        }
        return message;


    }
}
