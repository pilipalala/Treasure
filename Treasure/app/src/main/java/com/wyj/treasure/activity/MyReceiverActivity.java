package com.wyj.treasure.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MyReceiverActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;


    @Override
    protected int initView() {
        return R.layout.activity_my_receiver;
    }

    @Override
    protected void initData() {
        setTitle("发送自定义广播(标准)");
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        Intent intent = new Intent("com.wyj.treasure.MYRECEIVER");
        sendBroadcast(intent);
    }
}
