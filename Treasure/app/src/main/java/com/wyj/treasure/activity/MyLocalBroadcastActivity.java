package com.wyj.treasure.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyLocalBroadcastActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_dong)
    Button btnDong;
    @BindView(R.id.btn_jing)
    Button btnJing;
    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private LocalReceive localReceive;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_local_broadcast);
        ButterKnife.bind(this);
    }


    @Override
    protected void initData() {
        tvTitle.setText("发送本地广播");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        broadcastManager = LocalBroadcastManager.getInstance(this);


        /**
         * 1、通过LocalBroadcastManager.getInstance()方法得到他的实例
         * 2、创建IntentFilter的实例
         * 3、添加com.wyj.treasure.MYLOCALBROADCAST的action 广播接收器想要监听什么广播，就在这里添加什么样的action
         * 4、接下来创建广播实例
         * 5、调用LocalBroadcastManager的registerReceiver()方法进行注册
         */
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.wyj.treasure.MYLOCALBROADCAST");
        localReceive = new LocalReceive();
        /*注册本地广播监听器*/
        broadcastManager.registerReceiver(localReceive, intentFilter);
    }

    @OnClick({R.id.btn_dong, R.id.btn_jing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dong:
                Intent intent = new Intent("com.wyj.treasure.MYLOCALBROADCAST");
                broadcastManager.sendBroadcast(intent);
                break;
            case R.id.btn_jing:
                tvPrompt.setText("本地广播是无法通过静态注册方式来接收的。" +
                        "静态注册主要就是为了让程序在未启动的情况下也能收到广播，而发送本地广播时，" +
                        "我们的程序肯定时已经启动了，因此也完全不需要使用静态注册的功能");
                break;
        }
    }

    private class LocalReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtil.show("自定义动态注册本地广播");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(localReceive);
    }
}
