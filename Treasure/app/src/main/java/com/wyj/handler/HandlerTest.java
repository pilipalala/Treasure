package com.wyj.handler;

/**
 * Created by wangyujie
 * Date 2017/11/20
 * Time 1:40
 * TODO
 */

public class HandlerTest {
    public static void main(String[] arg) {
        Looper.prepare();

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {


            }
        };
        Looper.loop();
    }

}
