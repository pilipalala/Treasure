package com.wyj.treasure.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.wyj.treasure.R;

/**
 * @author wangyujie
 * @date 2018/10/18.16:04
 * @describe 添加描述
 */
public class NotificationUtils extends ContextWrapper {

    private final Bitmap mLargeIcon;
    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    public NotificationUtils(Context context) {
        super(context);
        mLargeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = null;
        channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.GREEN); //小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setNumber(3)//久按桌面图标时允许的此条通知的数量
                .setAutoCancel(true);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotificationWithAction(String title, String content, PendingIntent pi) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setFullScreenIntent(pi, true)
                .setContentIntent(pi)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setNumber(3)//久按桌面图标时允许的此条通知的数量
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setNumber(3) //久按桌面图标时允许的此条通知的数量
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setLargeIcon(mLargeIcon)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotificationWithAction_25(String title, String content, PendingIntent pi) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setFullScreenIntent(pi, true)
                .setContentIntent(pi)
                .setNumber(3) //久按桌面图标时允许的此条通知的数量
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setLargeIcon(mLargeIcon)
                .setAutoCancel(true);
    }

    public void sendNotification(int id, String title, String content) {
        Notification notification = getNotification(title, content);
        getManager().notify(id, notification);
    }

    private Notification getNotification(String title, String content) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            notification = getChannelNotification
                    (title, content).build();
        } else {
            notification = getNotification_25(title, content).build();
        }
        return notification;
    }

    /**
     * 设置FLAG_NO_CLEAR
     * 该 flag 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 requestCancel() 方法清除
     * Notification.flags属性可以通过 |= 运算叠加效果
     * //设置 Notification 的 flags = FLAG_NO_CLEAR
     * //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 requestCancel() 方法清除
     * //flags 可以通过 |= 运算叠加效果
     */
    public void sendFlagNoClearNotification(int id, String title, String content) {
        Notification notification = getNotification(title, content);
        notification.flags |= Notification.FLAG_NO_CLEAR;
        getManager().notify(id, notification);
    }

    public void sendNotificationWithAction(int id, String title, String content, PendingIntent pi) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            notification = getChannelNotificationWithAction
                    (title, content, pi).build();
        } else {
            notification = getNotificationWithAction_25(title, content, pi).build();
        }
        getManager().notify(id, notification);

    }
}
