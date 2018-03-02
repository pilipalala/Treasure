package com.wyj.process;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class IBinderSer extends Service {
    private final int MULTIPLE = 1024;
    private IBinder binder = new LocalBinder();

    public IBinderSer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 绑定服务，把当前服务的IBinder对象的引用传递给宿主
        return binder;
    }

    public int getMultipleNum(int num) {
        // 定义一个方法 用于数据交互
        return MULTIPLE * num;
    }

    public class LocalBinder extends Binder {
        // 在Binder中定义一个自定义的接口用于数据交互
        // 这里直接把当前的服务传回给宿主
        public IBinderSer getService() {
            return IBinderSer.this;
        }
    }
}
