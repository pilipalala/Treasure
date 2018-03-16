package com.wyj.handler;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


public class MyIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        // 调用父类的构造函数
        // 参数 = 工作线程的名字
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
