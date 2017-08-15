package com.wyj.treasure.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationStyleActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private NotificationManager manager;
    private Bitmap mLargeIcon;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_notification_style);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("Notification 样式");
        toolbar.setNavigationOnClickListener(v -> finish());
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mLargeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
    }

    @OnClick({R.id.btn_big_text_style, R.id.btn_inbox_style, R.id.btn_big_picture_style, R.id.btn_media_style, R.id.btn_priority_style})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_big_text_style:
                sendBigTextStyleNotification();
                break;
            case R.id.btn_inbox_style:
                sendInboxStyleNotification();
                break;
            case R.id.btn_big_picture_style:
                sendBigPictureStyleNotification();
                break;
            case R.id.btn_media_style:
                break;
            case R.id.btn_priority_style:
                sendPriorityStyleNotification();
                break;
        }
    }

    /**
     *
     */
    private void sendPriorityStyleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(mLargeIcon)
                .setContentText("通知的重要程度")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("我是只有文字效果的通知");
        manager.notify(1, builder.build());
    }

    private void sendBigPictureStyleNotification() {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(mLargeIcon);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("系统支持bigPictureStyle时显示的标题")
                .setContentText("bigPictureStyle")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(bigPictureStyle);
        manager.notify(3, builder.build());
    }

    private void sendInboxStyleNotification() {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("系统支持InboxStyle时显示的标题")
                .addLine("Line 1")
                .addLine("Line 2")
                .addLine("Line 6")
                .setSummaryText("+3 more");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("InboxStyle示例")
                .setContentText("InboxStyle演示示例")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(inboxStyle);
        manager.notify(2, builder.build());
    }

    /**
     * 发送一个BigTextStyle的通知
     */
    private void sendBigTextStyleNotification() {
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        /*相当于 setContentTitle()*/
        bigTextStyle.setBigContentTitle("系统支持BigTextStyle时显示的标题");
        /*bigText() 方法相当于 setContentText()*/
        bigTextStyle.bigText("系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n");
        //该方法没什么用，可以不设置
        bigTextStyle.setSummaryText("BigTextStyle SummaryText");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("BigTextStyle示例")
                .setContentText("BigTextStyle示例演示")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(bigTextStyle);
        manager.notify(1, builder.build());


    }
}
