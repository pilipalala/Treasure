package com.wyj.treasure.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.receiver.NetworkChangeReceiver;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkChangeActivity extends BaseActivity {
    @BindView(R.id.btn_sendBroadcast)
    Button btnSendBroadcast;
    @BindView(R.id.btn_sendOrderedBroadcast)
    Button btnSendOrderedBroadcast;
    @BindView(R.id.btn_localBroadcast)
    Button btnLocalBroadcast;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver receiver;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_network_change);
        ButterKnife.bind(this);
    }

    /**
     * 在代码中注册为动态注册
     * 动态注册一定要取消注册才行 unregisterReceiver(receiver);
     * 在AndroidManifest中注册为静态注册
     */
    @Override
    protected void initData() {
        tvTitle.setText("广播接收器");

        tvTitle.setOnClickListener(v -> ToastUtil.show("广播接收器"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    @OnClick({R.id.btn_sendBroadcast, R.id.btn_sendOrderedBroadcast, R.id.btn_localBroadcast})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_sendBroadcast:
                intent.setClass(this, MyReceiverActivity.class);
                break;
            case R.id.btn_sendOrderedBroadcast:
                intent.setClass(this, MyOrderlyBroadcastActivity.class);
                break;
            case R.id.btn_localBroadcast:
                intent.setClass(this, MyLocalBroadcastActivity.class);
                break;
        }
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
