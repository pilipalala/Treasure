package com.wyj.treasure.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * PendingIntent表示一种处于pending（待定、等待、即将发生）状态的意图；
 * PendingIntent通过send和cancel方法来发送和取消特定的待定Intent。
 * PendingIntent支持三种待定意图：启动Activity、启动Service和发送广播。分别对应：
 * getActivity / getService / getBroadcast
 * 参数相同，都为：(Context context, int requestCode, Intent intent, int flags)
 * 其中第二个参数，requestCode表示PendingIntent发送方的请求码，多少情况下为0即可，requestCode会影响到flags的效果。
 * <p>
 * PendingIntent的匹配规则是：如果两个PendingIntent他们内部的Intent相同并且requestCode也相同，那么这两个PendingIntent就是相同的。那么什么情况下Intent相同呢？
 * <p>
 * Intent的匹配规则是，如果两个Intent的ComponentName和intent-filter都相同；那么这两个Intent也是相同的。
 * flags参数的含义：
 * FLAG_ONE_SHOP 当前的PendingIntent只能被使用一次，然后他就会自动cancel，如果后续还有相同的PendingIntent，那么它们的send方法就会调用失败。
 * FLAG_NO_CREATE 当前描述的PendingIntent不会主动创建，如果当前PendingIntent之前存在，那么getActivity、getService和getBroadcast方法会直接返回Null，即获取PendingIntent失败，无法单独使用，平时很少用到。
 * FLAG_CANCEL_CURRENT 当前描述的PendingIntent如果已经存在，那么它们都会被cancel，然后系统会创建一个新的PendingIntent。对于通知栏消息来说，那些被cancel的消息单击后无法打开。
 * FLAG_UPDATE_CURRENT 当前描述的PendingIntent如果已经存在，那么它们都会被更新，即它们的Intent中的Extras会被替换为最新的。
 * <p>
 * <p>
 * NotificationManager的notify方法分析  manager.notify(1,notification);
 * 如果notify方法的id是常量，那么不管PendingIntent是否匹配，后面的通知都会替换掉前面的通知。
 * 如果notify的方法id每次都不一样，那么当PendingIntent不匹配的时候，不管在何种标记为下，这些通知都不会互相干扰。
 * 如果PendingIntent处于匹配阶段，分情况：
 * 采用FLAG_ONE_SHOT标记位，那么后续通知中的PendingIntent会和第一条通知保持一致，包括其中的Extras，单击任何一条通知后，其他通知均无法再打开；当所有通知被清除后。
 * 采用FLAG_CANCEL_CURRENT标记位，只有最新的通知可以打开，之前弹出的所有通知均无法打开。
 * 采用FLAG_UPDATE_CURRENT标记位，那么之前弹出的PendingIntent会被更新，最终它们和最新的一条保存完全一致，包括其中的Extras，并且这些通知都是可以打开的。
 */
public class NotificationStyleActivity extends BaseActivity {
    private NotificationManager manager;
    private Bitmap mLargeIcon;

    @Override
    protected int initView() {
        return R.layout.activity_notification_style;
    }

    @Override
    protected void initData() {
        setTitle("Notification 样式");
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
                sendMediaStyleNotification();
                break;
            case R.id.btn_priority_style:
                sendPriorityStyleNotification();
                break;
        }
    }

    /**
     * 在上面的代码中我们分别设置了bigContentView 这是展开的自定义视图，而contentView则是收起时的视图。
     * 注意bigContentView是在sdk16时引入的，所以需要判断一下。如果小于sdk16则只能定高了。
     * 注意bigContentView 的最大高度是256dp
     * 注意bigContentView和contentView的设置不能调转顺序，亲测这样会让contentView不显示。
     * 另外需要注意某些Rom可能不支持展开收起通知，在设置了BigContentView之后就只显示展开的视图，而默认情况下只展示收起视图。如魅族的FlyMe，其它Rom并没有测试，如果读者知道可以分享一下。
     */
    private void sendMediaStyleNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_medea);
        remoteViews.setTextViewText(R.id.tag_tv, "tag_tv");//设置文本内容
        remoteViews.setTextColor(R.id.tag_tv, Color.parseColor("#abcdef"));//设置文本颜色
        remoteViews.setTextViewText(R.id.tv_prompt, "tv_prompt");//设置文本内容
        remoteViews.setImageViewResource(R.id.iv_noty, R.mipmap.ic_launcher);//设置图片
        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);//设置RemoveViews点击后启动界面
//        设置不同区域的点击PendingIntent
        remoteViews.setOnClickPendingIntent(R.id.tag_tv, intent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText("setContentText")
                .setContentTitle("setContentTitle");
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = remoteViews;
        }
        notification.contentView = remoteViews;
        manager.notify(5, notification);
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
        manager.notify(4, builder.build());


    }
}
