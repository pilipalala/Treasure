package com.wyj.treasure.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.wyj.treasure.ProcessService;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

public class RemoteService extends Service {
    private MyBinder binder;
    private MyConn conn;
    private int MessageId = 1;

    public RemoteService() {
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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification());
        bindService(new Intent(RemoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);

        return START_STICKY;

    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ProcessService processService = ProcessService.Stub.asInterface(service);
            String serviceName = null;
            try {
                serviceName = processService.getServiceName();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            LogUtil.v(serviceName + "本地服务连接成功");
            boolean isServiceRunning = CommonUtils.isServiceWork(ProtectedService.class.getName());
            if (!isServiceRunning) {
                LogUtil.i("RemoteService " + isServiceRunning);
                Intent i = new Intent(RemoteService.this, ProtectedService.class);
                startService(i);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ToastUtil.show("本地服务被杀死");
            startService(new Intent(RemoteService.this, LocalService.class));
            bindService(new Intent(RemoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);
        }
    }

    public class MyBinder extends ProcessService.Stub {
        @Override
        public String getServiceName() {
            return "RemoteService";
        }
    }
}
