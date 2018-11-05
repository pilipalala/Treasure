package com.wyj.treasure.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.OnClick;

public class NotificationEffectActivity extends BaseActivity {
    private Bitmap mLargeIcon;
    private NotificationManager manager;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_notification_effect;
    }

    @Override
    protected void initData() {
        setTitle("Notification 提示形式");
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mLargeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);


    }

    @OnClick({R.id.btn_notify_only_text, R.id.btn_notify_with_ring, R.id.btn_notify_with_vibrate, R.id.btn_notify_with_lights, R.id.btn_notify_with_mix, R.id.btn_notify_with_insistent, R.id.btn_notify_with_alert_once, R.id.btn_clear_notify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_notify_only_text:
                showNotifyOnlyText();
                break;
            case R.id.btn_notify_with_ring:
                showNotifyWithRing();
                break;
            case R.id.btn_notify_with_vibrate:
                showNotifyWithVibrate();
                break;
            case R.id.btn_notify_with_lights:
                showNotifyWithLights();
                break;
            case R.id.btn_notify_with_mix:
                showNotifyWithMixed();
                break;
            case R.id.btn_notify_with_insistent:
                showInsistentNotify();
                break;
            case R.id.btn_notify_with_alert_once:
                showAlertOnceNotify();
                break;
            case R.id.btn_clear_notify:
                clearNotify();
                break;
        }
    }

    /**
     * 清除所有通知
     */
    private void clearNotify() {
        manager.cancelAll();
    }

    /**
     * 通知只执行一次,与默认的效果一样
     */
    private void showAlertOnceNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("仔细看,我就执行一遍")
                .setContentText("好了,已经一遍了~")
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notify = builder.build();
        notify.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        manager.notify(7, notify);
    }

    /**
     * 通知无限循环,直到用户取消或者打开通知栏(其实触摸就可以了),效果与FLAG_ONLY_ALERT_ONCE相反
     * 注:这里没有给Notification设置PendingIntent,也就是说该通知无法响应,所以只能手动取消
     */
    private void showInsistentNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("我是一个死循环,除非你取消或者响应")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentText("啦啦啦~");
        Notification notify = builder.build();
        notify.flags |= Notification.FLAG_INSISTENT;
        manager.notify(6, notify);


    }

    /**
     * 显示带有默认铃声、震动、呼吸灯效果的通知
     * 如需实现自定义效果,请参考前面三个例子
     */
    private void showNotifyWithMixed() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是有铃声+震动+呼吸灯效果的通知")
                .setContentText("我是最棒的~")
                //等价于setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                .setDefaults(Notification.DEFAULT_ALL);
        manager.notify(5, builder.build());
    }

    /**
     * 显示带有呼吸灯效果的通知,但是不知道为什么,自己这里测试没成功
     */
    private void showNotifyWithLights() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("我是带有呼吸灯效果的通知")
                .setContentText("一闪一闪亮晶晶~")
                //ledARGB 表示灯光颜色、 ledOnMS 亮持续时间、ledOffMS 暗的时间
                .setLights(Color.GREEN, 3000, 3000);
        manager.notify(4, builder.build());

    }

    /**
     * 展示有震动效果的通知，需要在AndroidManifest.xml中申请震动权限
     * <uses-permission android:name="android.permission.VIBRATE" />
     * 补充:测试震动的时候,手机的模式一定要调成铃声+震动模式,否则你是感受不到震动的
     */
    private void showNotifyWithVibrate() {
        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        /**
         * vibrate 是个长整型的数组，用于设置手机静止和振动的时长，以毫秒为单位，
         * 下标为0的值表示手机静止的时长，
         * 下标为1的值表示手机振动的时长，
         * 下标为2的值又表示手机静止的时长，以此类推。
         * */
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有震动效果的通知")
                .setContentText("颤抖吧,凡人~")
                /*使用系统默认的振动参数，会与自定义的冲突*/
//                .setDefaults(Notification.DEFAULT_VIBRATE)
                /*自定义振动效果*/
                .setVibrate(vibrate);

//        /*另一种设置振动的方法*/
//        Notification notify = builder.build();
//        /*设置系统默认振动*/
//        notify.defaults = Notification.DEFAULT_VIBRATE;
//        /*自定义的振动*/
//        notify.vibrate = vibrate;
//        manager.notify(3, notify);
        manager.notify(3, builder.build());
    }

    /**
     * 展示有自定义铃声效果的通知
     * 补充:使用系统自带的铃声效果:Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
     */
    private void showNotifyWithRing() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有铃声效果的通知")
                .setContentText("美妙么?安静听~")
                //调用系统默认响铃,设置此属性后setSound()会无效
//                .setDefaults(Notification.DEFAULT_SOUND);
                //调用系统多媒体裤内的铃声
//                .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2"));
                //调用自己提供的铃声
                .setSound(Uri.parse("android.resource://com.wyj.treasure/" + R.raw.sound));
        /*另一种设置铃声的方法*//*
        Notification notify = builder.build();
        *//*调用系统默认铃声*//*
        notify.defaults = Notification.DEFAULT_SOUND;
        *//*调用自己提供的铃声*//*
        notify.sound = Uri.parse("android.resource://com.wyj.treasure/" + R.raw.sound);
        *//*调用系统自带的铃声*//*
        notify.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "2");
        manager.notify(2, notify);*/
        manager.notify(2, builder.build());
    }

    /**
     * 最普通的通知效果
     */
    private void showNotifyOnlyText() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(mLargeIcon)
                .setFullScreenIntent(null, true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("我没有铃声、震动、呼吸灯,但我就是一个通知")
                        .setBigContentTitle("我是只有文字效果的通知"))
                .setContentTitle("我是只有文字效果的通知");
        manager.notify(1, builder.build());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLargeIcon != null) {
            if (!mLargeIcon.isRecycled()) {
                mLargeIcon.recycle();
            }
            mLargeIcon = null;
        }
    }
}
