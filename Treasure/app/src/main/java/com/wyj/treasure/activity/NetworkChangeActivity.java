package com.wyj.treasure.activity;


import com.wyj.treasure.R;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;

public class NetworkChangeActivity extends BaseActivity {

    @Override
    protected List<ItemInfo> getListData() {
        mData.add(new ItemInfo("发送自定义广播(标准)", MyReceiverActivity.class, 0));
        mData.add(new ItemInfo("发送自定义广播(有序)", MyOrderlyBroadcastActivity.class, 0));
        mData.add(new ItemInfo("发送本地广播", MyLocalBroadcastActivity.class, 0));
        return super.getListData();
    }

    @Override
    protected int getContentViewID() {
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
    }

}
