package com.wyj.mvp.service.retrofit;

/**
 * @author wangyujie
 * @date 2018/9/19.15:13
 * @describe 添加描述
 */
public class HttpResult<T> {
    private int code;
    private String message;
    private String title;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
