package com.wyj.treasure.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin
 * on 2017/8/14.
 * TODO Notification
 * <p>
 */
public class SimpleNotificationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private NotificationManager notifyManager;
    public static final int DEFAULT_NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_TAG = "littlejie";
    private Bitmap mLargeIcon;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_simple_notification);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("使用通知栏");
        toolbar.setNavigationOnClickListener(v -> finish());


        /**1、首先需要一个 NotificationManager 来对通知进行管理，
         * 可以调用 Context 的  getSystemService()方法获取到。
         * getSystemService()方法接收一个字符串参数用于确定获取系统的 哪个 服务 ，
         * 这 里我们 传入 Context.NOTIFICATION_SERVICE 即可。因 此， 获取NotificationManager 的实例就可以写成：
         * */

        notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mLargeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

    }

    private void sendNotification() {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle("最简单的Notification")
                //设置通知内容
                .setContentText("只有小图标、标题、内容");
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(DEFAULT_NOTIFICATION_ID, builder.build());
    }


    /**
     * 发送一个点击跳转到MainActivity的消息
     */
    private void sendSimplestNotificationWithAction() {
        //获取PendingIntent
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("我是带Action的Notification")
                .setContentText("点我会打开MainActivity")
                .setContentIntent(mainPendingIntent);
        //发送通知
        notifyManager.notify(3, builder.build());
    }

    /**
     * 使用notify(String tag, int id, Notification notification)方法发送通知
     * 移除对应通知需使用 cancel(String tag, int id)
     */
    private void sendNotificationWithTag() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Send Notification With Tag")
                .setContentText("Hi,My id is 1,tag is " + NOTIFICATION_TAG);
        notifyManager.notify(NOTIFICATION_TAG, DEFAULT_NOTIFICATION_ID, builder.build());
    }

    /**
     * 循环发送十个通知
     */
    private void sendTenNotifications() {
        for (int i = 0; i < 10; i++) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Send Notification Batch")
                    .setContentText("Hi,My id is " + i);
            notifyManager.notify(i, builder.build());
        }
    }

    /**
     * 设置FLAG_NO_CLEAR
     * 该 flag 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
     * Notification.flags属性可以通过 |= 运算叠加效果
     */
    private void sendFlagNoClearNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Send Notification Use FLAG_NO_CLEAR")
                .setContentText("Hi,My id is 1,i can't be clear.");
        Notification notification = builder.build();
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果

        notification.flags |= Notification.FLAG_NO_CLEAR;
        notifyManager.notify(DEFAULT_NOTIFICATION_ID, notification);
    }

    /**
     * 设置FLAG_AUTO_CANCEL
     * 该 flag 表示用户单击通知后自动消失
     */
    private void sendFlagAutoCancelNotification() {
        //设置一个Intent,不然点击通知不会自动消失
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Send Notification Use FLAG_AUTO_CLEAR")
                .setContentText("Hi,My id is 1,i can be clear.")
                .setContentIntent(resultPendingIntent);
        Notification notification = builder.build();
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_AUTO_CANCEL 表示该通知能被状态栏的清除按钮给清除掉
        //等价于 builder.setAutoCancel(true);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notifyManager.notify(DEFAULT_NOTIFICATION_ID, notification);
    }

    /**
     * 设置FLAG_ONGOING_EVENT
     * 该 flag 表示发起正在运行事件（活动中）
     */
    private void sendFlagOngoingEventNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Send Notification Use FLAG_ONGOING_EVENT")
                .setContentText("Hi,My id is 1,i can't be clear.");
        Notification notification = builder.build();
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_ONGOING_EVENT 表示该通知通知放置在正在运行,不能被手动清除,但能通过 cancel() 方法清除
        //等价于 builder.setOngoing(true);
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notifyManager.notify(DEFAULT_NOTIFICATION_ID, notification);
    }
    /**
     * 发送一个简单的通知,只带有小图标、标题、内容
     */
    private void sendSimplestNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("最简单的Notification")
                .setContentText("只有小图标、标题、内容");
        notifyManager.notify(1, builder.build());
    }
    /**
     * 发送一个具有大图标的简单通知
     * 当setSmallIcon与setLargeIcon同时存在时,smallIcon显示在通知的右下角,largeIcon显示在左侧
     * 当只设置setSmallIcon时,smallIcon显示在左侧
     */
    private void sendSimplestNotificationWithLargeIcon() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("带大图标的Notification")
                .setContentText("有小图标、大图标、标题、内容")
                .setLargeIcon(mLargeIcon);
        notifyManager.notify(2, builder.build());
    }

    @OnClick({R.id.btn_send_simplest_notification, R.id.btn_send_simplest_notification_with_large_icon, R.id.btn_send_simplest_notification_with_action,R.id.btn_remove_all_notification, R.id.btn_send_notification, R.id.btn_remove_notification, R.id.btn_send_notification_with_tag, R.id.btn_remove_notification_with_tag, R.id.btn_send_ten_notification, R.id.btn_send_flag_no_clear_notification, R.id.btn_send_flag_ongoing_event_notification, R.id.btn_send_flag_auto_cancecl_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_remove_all_notification:
                //移除当前 Context 下所有 Notification,包括 FLAG_NO_CLEAR 和 FLAG_ONGOING_EVENT
                notifyManager.cancelAll();
                break;
            case R.id.btn_send_notification:
                //发送一个 Notification,此处 ID = 1
                sendNotification();
                break;
            case R.id.btn_remove_notification:
                //移除 ID = 1 的 Notification,注意:该方法只针对当前 Context。
                notifyManager.cancel(DEFAULT_NOTIFICATION_ID);
                break;
            case R.id.btn_send_notification_with_tag:
                //发送一个 ID = 1 并且 TAG = littlejie 的 Notification
                //注意:此处发送的通知与 sendNotification() 发送的通知并不冲突
                //因为此处的 Notification 带有 TAG
                sendNotificationWithTag();
                break;
            case R.id.btn_remove_notification_with_tag:
                //移除一个 ID = 1 并且 TAG = littlejie 的 Notification
                //注意:此处移除的通知与 NotificationManager.cancel(int id) 移除通知并不冲突
                //因为此处的 Notification 带有 TAG
                notifyManager.cancel(NOTIFICATION_TAG, DEFAULT_NOTIFICATION_ID);
                break;
            case R.id.btn_send_ten_notification:
                //连续发十条 Notification
                sendTenNotifications();
                break;
            case R.id.btn_send_flag_no_clear_notification:
                //发送 ID = 1, flag = FLAG_NO_CLEAR 的 Notification
                //下面两个 Notification 的 ID 都为 1,会发现 ID 相等的 Notification 会被最新的替换掉
                sendFlagNoClearNotification();
                break;
            case R.id.btn_send_flag_ongoing_event_notification:
                sendFlagOngoingEventNotification();
                break;
            case R.id.btn_send_flag_auto_cancecl_notification:
                sendFlagAutoCancelNotification();
                break;
            case R.id.btn_send_simplest_notification:
                sendSimplestNotification();
                break;
            case R.id.btn_send_simplest_notification_with_large_icon:
                sendSimplestNotificationWithLargeIcon();
                break;
            case R.id.btn_send_simplest_notification_with_action:
                sendSimplestNotificationWithAction();
                break;
        }
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
