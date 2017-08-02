package com.wyj.treasure.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wyj.treasure.utils.ToastUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtil.show(context, "network changes");
    }
}
