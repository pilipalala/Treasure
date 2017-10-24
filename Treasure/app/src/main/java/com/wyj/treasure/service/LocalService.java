package com.wyj.treasure.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.wyj.treasure.ProcessService;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

public class LocalService extends Service {
    private MyBinder binder;
    private MyConn conn;
    private int MessageId = 1;

    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        if (conn == null) {
            conn = new MyConn();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    LogUtil.v("测试");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(LocalService.this, RemoteService.class), conn, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.v("连接远程服务成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ToastUtil.show("远程服务被杀死");
            startService(new Intent(LocalService.this, RemoteService.class));
            bindService(new Intent(LocalService.this, RemoteService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    public class MyBinder extends ProcessService.Stub {
        @Override
        public String getServiceName() {
            return "Service";
        }
    }
}
