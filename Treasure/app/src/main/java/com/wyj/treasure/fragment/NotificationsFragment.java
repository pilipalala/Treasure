package com.wyj.treasure.fragment;


import com.wyj.treasure.R;
import com.wyj.treasure.activity.NotificationStyleActivity;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.notification.NotificationEffectActivity;
import com.wyj.treasure.notification.SimpleNotificationActivity;

public class NotificationsFragment extends BaseCardViewFragment {


    @Override
    protected void processData() {
        mData.add(new ItemInfo("Notification 简单 Demo", SimpleNotificationActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Notification 提示形式", NotificationEffectActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Notification 样式", NotificationStyleActivity.class, R.mipmap.ic_launcher));

        notifyDataChanged();
    }

}
