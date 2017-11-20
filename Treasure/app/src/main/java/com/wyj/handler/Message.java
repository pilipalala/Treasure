package com.wyj.handler;

/**
 * Created by wangyujie
 * Date 2017/11/19
 * Time 19:48
 * TODO
 */

public class Message {
    Handler target;
    public int what;
    public Object obj;

    @Override
    public String toString() {
        return obj.toString();
    }
}
