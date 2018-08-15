package com.wyj.notification;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.activity.MainActivity;
import com.wyj.treasure.activity.NotificationStyleActivity;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.notification.NotificationEffectActivity;
import com.wyj.treasure.notification.SimpleNotificationActivity;
import com.wyj.treasure.utils.MyUtils;

import java.util.List;

public class NotificationActivity extends BaseActivity {


    @Override
    protected int initView() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initData() {
        setTitle("通知栏样式");
        initNotification();
    }

    @Override
    protected List<ItemInfo> getListData() {
        mData.add(new ItemInfo("Notification 简单 Demo", SimpleNotificationActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Notification 提示形式", NotificationEffectActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Notification 样式", NotificationStyleActivity.class, R.mipmap.ic_launcher));
        return mData;
    }
    /**
     * 获取通知栏权限
     * 如果没有打开通知权限则跳到设置界面
     */
    public void initNotification() {
        boolean enabled = MyUtils.isNotificationEnabled(this);
        if (!enabled) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("请开启通知栏权限！").setNegativeButton("不去", null).setPositiveButton("好", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyUtils.toSetting(NotificationActivity.this);
                }
            }).show();
        }
    }
}
