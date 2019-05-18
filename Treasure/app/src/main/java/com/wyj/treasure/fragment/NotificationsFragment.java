package com.wyj.treasure.fragment;


import com.wyj.alarm.AlarmActivity;
import com.wyj.bluetooth.BluetoothBleActivity;
import com.wyj.gifview.GifPlayActivity;
import com.wyj.greendao.GreenDAOActivity;
import com.wyj.handler.CustomHandlerActivity;
import com.wyj.handler.HandlerThreadActivity;
import com.wyj.handler.NewHandlerThreadActivity;
import com.wyj.mvp.ui.activity.BusActivity;
import com.wyj.mvp.ui.activity.RetrofitActivity;
import com.wyj.mvp.ui.activity.ZhiHuDailyActivity;
import com.wyj.notification.NotificationActivity;
import com.wyj.realm.RealmActivity;
import com.wyj.setdetail.SettingDetailActivity;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.RemoteViewWidgetActivity;
import com.wyj.treasure.activity.VolumeAdjustmentActivity;
import com.wyj.treasure.activity.itemtouch.RecyclerViewItemTouchActivity;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.youtube.YoutubeActivity;

public class NotificationsFragment extends BaseCardViewFragment {


    @Override
    protected void processData() {
        mData.add(new ItemInfo("Youtube缩放动效", YoutubeActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("设置闹钟", AlarmActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Notification", NotificationActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("RemoteView 桌面小部件", RemoteViewWidgetActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("拖动排序效果", RecyclerViewItemTouchActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("HandlerThread", HandlerThreadActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("CustomHandler", CustomHandlerActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("NewHandlerThreadActivity", NewHandlerThreadActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("蓝牙", BluetoothBleActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("调节音量", VolumeAdjustmentActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("播放gif图片", GifPlayActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("打开系统设置界面大全", SettingDetailActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("知乎日报", ZhiHuDailyActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Retrofit", RetrofitActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("greenDAO", GreenDAOActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Realm", RealmActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("BUS", BusActivity.class, R.mipmap.ic_launcher));

        notifyDataChanged();
    }

}
