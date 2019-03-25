package com.wyj.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetWorkActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView mText;
    private MyReceiver receiver;
    private NetInfo netInfo;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_net_work;
    }

    @Override
    protected void initData() {
        IntentFilter filter = new IntentFilter("com.NETWORK_INFO");
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class MyReceiver extends BroadcastReceiver {

        public MyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {

            netInfo = intent.getParcelableExtra("NETWORK_INFO");
            int type = netInfo.getType();
            StringBuilder builder = new StringBuilder("当前网络的类型为:");
            switch (type) {
                case 0:
                    builder.append("无网络");
                    break;
                case 1:
                    builder.append("Wifi");
                    break;
                case 2:
                    builder.append("2G");
                    break;
                case 3:
                    builder.append("3G");
                    break;
                case 4:
                    builder.append("4G");
                    break;
                case 5:
                    builder.append("移动网络");
                    break;
            }
            builder.append(" 强度为：" + (netInfo.getStrength() + 100) + " 当前网络的级别为：" + netInfo.getLevel());
            mText.setText(builder.toString());
            Toast.makeText(context, "收到广播，网络变化", Toast.LENGTH_SHORT).show();
        }
    }
}
