package com.wyj.treasure.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.MainActivity;
import com.wyj.treasure.utils.LogUtil;

public class MyService extends Service {
    private int MessageId = 1;

    public MyService() {
    }

    public class DownloadBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

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
       /* Intent intent = new Intent(this, BusActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("点击可以跳转到Activity")
                .setContentText("~~~~~")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1000, notification);*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("onStartCommand");
        /*提高优先级*/
        intent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("点击可以跳转到Activity")
                .setContentText("~~~~~")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        startForeground(MessageId, notification);

        return START_STICKY;
    }

}
