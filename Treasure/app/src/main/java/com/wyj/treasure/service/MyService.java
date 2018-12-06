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
import com.wyj.treasure.utils.ToastUtil;

/**
 * @author wangyujie
 * @date 2018/11/9.14:57
 * @describe Service的生命周期 (在 sdk 2.0 及其以后的版本中，对应的 onStart 已经被否决变为了 onStartCommand)
 * onCreate　　onStart　　onDestroy　　onBind
 * 1、被启动的服务的生命周期：如果一个Service被某个Activity 调用 Context.startService 方法启动，
 * 那么不管是否有Activity使用bindService绑定或unbindService解除绑定到该Service，
 * 该Service都在后台运行。如果一个Service被startService 方法多次启动，
 * 那么onCreate方法只会调用一次，onStart将会被调用多次（对应调用startService的次数），
 * 并且系统只会创建Service的一个实例（因此你应该知道只需要一次stopService调用）。
 * 该Service将会一直在后台运行，而不管对应程序的Activity是否在运行，直到被调用stopService，
 * 或自身的stopSelf方法。当然如果系统资源不足，android系统也可能结束服务。
 * <p>
 * 2、被绑定的服务的生命周期：如果一个Service被某个Activity 调用 Context.bindService 方法绑定启动，
 * 不管调用 bindService 调用几次，onCreate方法都只会调用一次，同时onStart方法始终不会被调用。
 * 当连接建立之后，Service将会一直运行，除非调用Context.unbindService 断开连接或者之前调用bindService
 * 的 Context 不存在了（如Activity被finish的时候），系统将会自动停止Service，对应onDestroy将被调用。
 * <p>
 * 3、被启动又被绑定的服务的生命周期：如果一个Service又被启动又被绑定，则该Service将会一直在后台运行。
 * 并且不管如何调用，onCreate始终只会调用一次，对应startService调用多少次，Service的onStart便会调用多少次。
 * 调用unbindService将不会停止Service，而必须调用 stopService 或 Service的 stopSelf 来停止服务。
 * <p>
 * 4、当服务被停止时清除服务：当一个Service被终止（1、调用stopService；2、调用stopSelf；3
 * 、不再有绑定的连接（没有被启动））时，onDestroy方法将会被调用，在这里你应当做一些清除工作，
 * 如停止在Service中创建并运行的线程。
 * <p>
 * <p>
 * 在 AndroidManifest.xml 里 Service 元素的常见选项
 * <p>
 * android:name　　-------------　　服务类名
 * <p>
 * android:label　　--------------　　服务的名字，如果此项不设置，那么默认显示的服务名则为类名
 * <p>
 * android:icon　　--------------　　服务的图标
 * <p>
 * android:permission　　-------　　申明此服务的权限，这意味着只有提供了该权限的应用才能控制或连接此服务
 * <p>
 * android:process　　----------　　表示该服务是否运行在另外一个进程，如果设置了此项，那么将会在包名后面加上这段字符串表示另一进程的名字
 * <p>
 * android:enabled　　----------　　如果此项设置为 true，那么 Service 将会默认被系统启动，不设置默认此项为 false
 * <p>
 * android:exported　　---------　　表示该服务是否能够被其他应用程序所控制或连接，不设置默认此项为 false
 */
public class MyService extends Service {

    private int MessageId = 1;

    public MyService() {
    }

    public class DownloadBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

        /**
         * 获取 Service 实例
         *
         * @return
         */
        public MyService getService() {
            return MyService.this;
        }

        public void startDownload() {
            LogUtil.d("startDownload");
            ToastUtil.show("绑定成功,开始下载");
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
        // 注意使用  startForeground ，id 为 0 将不会显示 notification
        startForeground(MessageId, notification);

        return START_STICKY;
    }

}
