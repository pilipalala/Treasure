package com.wyj.treasure.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderlyBroadcastActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn)
    Button btn;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_receiver);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        tvTitle.setText("发送自定义广播(有序)");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 发送有序广播只需 将 sendBroadcast() 方法改成 sendOrderedBroadcast()方法。
     * sendOrderedBroadcast()方法接收两个参数，第一个参数仍然是intent，第二个参数是一个与 权限相关的字符串
     */
    @OnClick(R.id.btn)
    public void onViewClicked() {
        Intent intent = new Intent("com.wyj.treasure.ORDEREDRECEIVER");
        sendOrderedBroadcast(intent, null);
    }
}
