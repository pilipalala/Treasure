package com.wyj.treasure.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.wyj.treasure.activity.MainActivity;
import com.wyj.treasure.utils.LogUtil;

public class MyService extends Service {
    public MyService() {
    }

    public class DownloadBinder extends Binder {
        public void startDownload() {
            LogUtil.d("startDownload");
        }

        public int getProgress() {
            LogUtil.d("downloadPress");
            return 0;
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("onBind");
        return new DownloadBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("onCreate");


        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivities()
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("onStartCommand");






        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");

    }
}
