package com.wyj.treasure.fragment;


import com.wyj.bluetooth.BluetoothActivity;
import com.wyj.handler.CustomHandlerActivity;
import com.wyj.handler.HandlerThreadActivity;
import com.wyj.handler.NewHandlerThreadActivity;
import com.wyj.notification.NotificationActivity;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.RemoteViewWidgetActivity;
import com.wyj.treasure.activity.VolumeAdjustmentActivity;
import com.wyj.treasure.activity.itemtouch.RecyclerViewItemTouchActivity;
import com.wyj.treasure.mode.ItemInfo;

public class NotificationsFragment extends BaseCardViewFragment {


    @Override
    protected void processData() {
        mData.add(new ItemInfo("Notification", NotificationActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("RemoteView 桌面小部件", RemoteViewWidgetActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("拖动排序效果", RecyclerViewItemTouchActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("HandlerThread", HandlerThreadActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("CustomHandler", CustomHandlerActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("NewHandlerThreadActivity", NewHandlerThreadActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("蓝牙", BluetoothActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("调节音量", VolumeAdjustmentActivity.class, R.mipmap.ic_launcher));

        notifyDataChanged();
    }

}
