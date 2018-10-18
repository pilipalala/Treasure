package com.wyj.treasure.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.receiver.NetworkChangeReceiver;
import com.wyj.treasure.utils.ToastUtil;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.requestNetwork(new NetworkRequest.Builder().build(),
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onLost(Network network) {
                            super.onLost(network);
                            ToastUtil.show("网络不可用");
                        }

                        @Override
                        public void onAvailable(Network network) {
                            super.onAvailable(network);
                            ToastUtil.show("网络可用");
                        }
                    });
        } else {
            intentFilter = new IntentFilter();
            /*动态注册*/
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            receiver = new NetworkChangeReceiver();
            registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

}
