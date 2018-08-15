package com.wyj.treasure.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wyj.treasure.R;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.receiver.NetworkChangeReceiver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkChangeActivity extends BaseActivity {
    private IntentFilter intentFilter;
    private NetworkChangeReceiver receiver;

    @Override
    protected List<ItemInfo> getListData() {
        mData.add(new ItemInfo("发送自定义广播(标准)", MyReceiverActivity.class, 0));
        mData.add(new ItemInfo("发送自定义广播(有序)", MyOrderlyBroadcastActivity.class, 0));
        mData.add(new ItemInfo("发送本地广播", MyLocalBroadcastActivity.class, 0));
        return super.getListData();
    }

    @Override
    protected int initView() {
        return R.layout.activity_network_change;
    }

    /**
     * 在代码中注册为动态注册
     * 动态注册一定要取消注册才行 unregisterReceiver(receiver);
     * 在AndroidManifest中注册为静态注册
     */
    @Override
    protected void initData() {
        setTitle("广播接收器");
        intentFilter = new IntentFilter();
        /*动态注册*/
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}
