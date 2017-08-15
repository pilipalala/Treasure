package com.wyj.treasure.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wyj.treasure.utils.ToastUtil;

public class MyOrderedReceiver extends BroadcastReceiver {
    /**
     * <intent-filter android:priority="100">
     * <action android:name="com.wyj.treasure.MYRECEIVER" />
     * </intent-filter>
     * 通过android:priority 属性给广播设置了优先级，优先级比较高的广播接收器就可以先收到广播
     * <p>
     * 既然已经获得 了 接收广播的优先权，那么 MyOrderedReceiver就可以选择是否允许广播继续传递
     * <p>
     * <p>
     * 如果在onReceive()方法中调用了abortBroadcast()方法，就表示这条广播截断，后面的广播接收器无法再接收到这条广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtil.show("自定义有序广播");
//        abortBroadcast();
    }
}
