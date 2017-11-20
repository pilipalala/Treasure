package com.wyj.handler;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;

import java.util.UUID;

/**
 * Created by wangyujie
 * Date 2017/11/20
 * Time 1:40
 * TODO
 */

public class HandlerActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_handler);
    }

    @Override
    protected void initData() {
        //轮询器初始化
        Looper.prepare();

        //主线程
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                LogUtil.d(Thread.currentThread().getName() + ",received" + msg.toString());
            }
        };
        //子线程

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        Message msg = new Message();
                        msg.what = 1;
                        synchronized (UUID.class) {
                            msg.obj = Thread.currentThread().getName() + ",send message:" + UUID.randomUUID().toString();
                        }
                        LogUtil.d(msg.toString());
                        handler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }

                    }

                }
            }.start();
        }
        //开启轮询
        Looper.loop();
    }
}
